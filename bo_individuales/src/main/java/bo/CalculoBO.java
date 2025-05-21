/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import dao.CalculoDAO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.ICalculoDAO;
import java.time.LocalDate;
import java.util.List;
import mappers.CalculoMapper;
import negocio_dto.CalculoDTO;
import negocio_dto.ElementoDTO;
import negocio_dto.MaterialCalculoDTO;
import negocio_dto.ObraDTO;

/**
 * Clase CalculoBO
 *
 * Objeto de negocio (Business Object) que gestiona la lógica relacionada con
 * los cálculos de material en el sistema. Esta clase implementa el patrón
 * Singleton para garantizar una única instancia en toda la aplicación.
 *
 * @author Alejandra García Preciado - 252444
 */
public class CalculoBO {

    /**
     * Instancia única de la clase (patrón Singleton). Garantiza que solo exista
     * una instancia en toda la aplicación.
     */
    public static CalculoBO instance;

    /**
     * DAO para calculos. Gestiona el acceso a los datos de calculos en la
     * persistencia.
     */
    private ICalculoDAO calculoDAO;

    /**
     * Constructor privado (patrón Singleton). Previene la creación de múltiples
     * instancias desde fuera de la clase.
     */
    private CalculoBO() {
        this.calculoDAO = new CalculoDAO();
    }

    /**
     * Método para obtener la instancia única de CalculoBO (patrón Singleton).
     * Si no existe una instancia, la crea; de lo contrario, devuelve la
     * existente.
     *
     * @return La instancia única de CalculoBO
     */
    public static CalculoBO getInstance() {
        if (instance == null) {
            instance = new CalculoBO();
        }
        return instance;
    }

    /**
     * Guarda un nuevo cálculo de materiales en la base de datos.
     *
     * @param calculoDTO Cálculo a guardar
     * @return Cálculo guardado con su ID asignado
     * @throws NegocioException Si ocurre un error durante el guardado o
     * validación
     */
    public CalculoDTO guardarCalculo(CalculoDTO calculoDTO) throws NegocioException {
        // Validar el cálculo
        validarCalculo(calculoDTO);

        try {
            return CalculoMapper.toDTO(calculoDAO.guardarCalculo(CalculoMapper.toEntity(calculoDTO)));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al guardar el cálculo en la base de datos", ex);
        }
    }

    /**
     * Busca un cálculo por su identificador único.
     *
     * @param id Identificador único del cálculo
     * @return Cálculo encontrado o null si no existe
     * @throws NegocioException Si ocurre un error durante la búsqueda
     */
    public CalculoDTO buscarPorId(String id) throws NegocioException {
        try {
            return CalculoMapper.toDTO(calculoDAO.buscarPorId(id));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar cálculo por ID: " + id, ex);
        }
    }

    /**
     * Busca todos los cálculos asociados a una obra específica.
     *
     * @param idObra Identificador único de la obra
     * @return Lista de cálculos asociados a la obra
     * @throws NegocioException Si ocurre un error durante la búsqueda
     */
    public List<CalculoDTO> buscarPorObra(String idObra) throws NegocioException {
        try {
            return CalculoMapper.toDTOList(calculoDAO.buscarPorObra(idObra));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar cálculos por obra: " + idObra, ex);
        }
    }

    /**
     * Obtiene el historial de cálculos de una obra específica.
     *
     * @param idObra Identificador único de la obra
     * @return Lista de cálculos que conforman el historial
     * @throws NegocioException Si ocurre un error durante la consulta
     */
    public List<CalculoDTO> obtenerHistorial(String idObra) throws NegocioException {
        return buscarPorObra(idObra);
    }

    /**
     * Crea un nuevo cálculo para un elemento de una obra.
     *
     * @param obraDTO Obra a la que pertenece el elemento
     * @param elementoDTO Elemento para el que se realizará el cálculo
     * @return Cálculo creado con los materiales calculados
     * @throws NegocioException Si ocurre un error durante el cálculo o
     * validación
     */
    public CalculoDTO crearCalculo(ObraDTO obraDTO, ElementoDTO elementoDTO) throws NegocioException {
        // Validar que la obra y el elemento no sean nulos
        if (obraDTO == null) {
            throw new NegocioException("La obra no puede ser nula");
        }

        if (elementoDTO == null) {
            throw new NegocioException("El elemento no puede ser nulo");
        }

        // Validar dimensiones del elemento
        if (!ElementoBO.getInstance().validarDimensiones(elementoDTO)) {
            throw new NegocioException("Las dimensiones del elemento no son válidas");
        }

        try {
            // Calcular los materiales necesarios
            List<MaterialCalculoDTO> materialesCalculados = MaterialCalculoBO.getInstance().calcularMateriales(elementoDTO);

            // Calcular el volumen total
            Double volumenCalculado = calcularVolumen(elementoDTO);

            // Crear el objeto CalculoDTO
            CalculoDTO calculoDTO = new CalculoDTO(
                    obraDTO,
                    LocalDate.now(),
                    volumenCalculado,
                    elementoDTO,
                    materialesCalculados
            );

            // Guardar en la base de datos
            return guardarCalculo(calculoDTO);
        } catch (NegocioException ex) {
            throw new NegocioException("Error al crear el cálculo", ex);
        }
    }

    /**
     * Valida que un cálculo sea correcto antes de guardarlo.
     *
     * @param calculoDTO Cálculo a validar
     * @throws NegocioException Si el cálculo no es válido
     */
    private void validarCalculo(CalculoDTO calculoDTO) throws NegocioException {
        if (calculoDTO == null) {
            throw new NegocioException("El cálculo no puede ser nulo");
        }

        if (calculoDTO.getObra() == null) {
            throw new NegocioException("La obra asociada al cálculo no puede ser nula");
        }

        if (calculoDTO.getElemento() == null) {
            throw new NegocioException("El elemento del cálculo no puede ser nulo");
        }

        if (calculoDTO.getMaterialesCalculados() == null || calculoDTO.getMaterialesCalculados().isEmpty()) {
            throw new NegocioException("La lista de materiales calculados no puede ser nula o vacía");
        }

        // Validar dimensiones del elemento
        if (!ElementoBO.getInstance().validarDimensiones(calculoDTO.getElemento())) {
            throw new NegocioException("Las dimensiones del elemento no son válidas");
        }

        // Validar que el volumen sea correcto
        Double volumenCalculado = calcularVolumen(calculoDTO.getElemento());
        if (Math.abs(volumenCalculado - calculoDTO.getVolumenCalculado()) > 0.001) {
            throw new NegocioException("El volumen calculado no coincide con las dimensiones del elemento");
        }
    }

    /**
     * Calcula el volumen de un elemento constructivo basado en su tipo y
     * dimensiones.
     *
     * @param elemento Elemento para el que se calculará el volumen
     * @return Volumen calculado en metros cúbicos
     */
    private Double calcularVolumen(ElementoDTO elemento) {
        Double volumen = 0.0;

        switch (elemento.getTipo()) {
            case COLUMNA_CUADRADA:
                volumen = elemento.getAlto() * elemento.getAncho() * elemento.getAncho();
                break;

            case LOSA_CONTRAPISO:
            case LOSA_ENTREPISO:
                volumen = elemento.getLargo() * elemento.getAncho() * elemento.getEspesor();
                break;

            case VIGA:
                volumen = elemento.getLargo() * elemento.getAncho() * elemento.getAlto();
                break;

            case MURO_LADRILLO:
                volumen = elemento.getLargo() * elemento.getAlto() * elemento.getEspesor();
                break;

            case NIVELACION_MUROS_VERTICAL:
            case NIVELACION_PISOS_HORIZONTAL:
                volumen = elemento.getLargo() * elemento.getAncho() * elemento.getEspesor();
                break;

            default:
                volumen = 0.0;
        }

        return volumen;
    }

}
