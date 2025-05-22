/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import dao.MaterialCalculoDAO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IMaterialCalculoDAO;
import java.util.ArrayList;
import java.util.List;
import mappers.MaterialCalculoMapper;
import negocio_dto.ElementoDTO;
import negocio_dto.MaterialCalculoDTO;
import negocio_enums.TipoMaterialNegocio;
import negocio_enums.UnidadMaterialNegocio;

/**
 * Clase MaterialCalculoBO
 *
 * Objeto de negocio (Business Object) que gestiona la lógica relacionada con
 * los materiales calculados en los cálculos del sistema. Esta clase implementa
 * el patrón Singleton para garantizar una única instancia en toda la
 * aplicación.
 *
 * @author Alejandra García Preciado - 252444
 */
public class MaterialCalculoBO {

    /**
     * Instancia única de la clase (patrón Singleton). Garantiza que solo exista
     * una instancia en toda la aplicación.
     */
    public static MaterialCalculoBO instance;

    /**
     * DAO para materiales calculados. Gestiona el acceso a los datos de los
     * materiales calculados en la persistencia.
     */
    private IMaterialCalculoDAO materialCalculoDAO;

    /**
     * Constructor privado (patrón Singleton). Previene la creación de múltiples
     * instancias desde fuera de la clase.
     */
    private MaterialCalculoBO() {
        this.materialCalculoDAO = new MaterialCalculoDAO();
    }

    /**
     * Método para obtener la instancia única de MaterialCalculoBO (patrón
     * Singleton). Si no existe una instancia, la crea; de lo contrario,
     * devuelve la existente.
     *
     * @return La instancia única de MaterialCalculoBO
     */
    public static MaterialCalculoBO getInstance() {
        if (instance == null) {
            instance = new MaterialCalculoBO();
        }
        return instance;
    }

    /**
     * Calcula los materiales necesarios para un elemento específico basado en
     * sus dimensiones.
     *
     * @param elementoDTO El elemento para el cual se calcularán los materiales
     * @return Lista de materiales calculados para el elemento
     * @throws NegocioException Si ocurre un error durante el cálculo
     */
    public List<MaterialCalculoDTO> calcularMateriales(ElementoDTO elementoDTO) throws NegocioException {
        if (elementoDTO == null) {
            throw new NegocioException("El elemento no puede ser nulo");
        }

        try {
            // Calcular el volumen del elemento
            Double volumen = calcularVolumen(elementoDTO);

            // Obtener la lista de materiales necesarios según el tipo de elemento
            List<MaterialCalculoDTO> materiales = calcularMaterialesSegunTipo(elementoDTO, volumen);

            return materiales;
        } catch (Exception ex) {
            throw new NegocioException("Error al calcular materiales para el elemento", ex);
        }
    }

    /**
     * Busca materiales por su tipo en la base de datos.
     *
     * @param tipo Tipo de material a buscar
     * @return Lista de materiales del tipo especificado
     * @throws NegocioException Si ocurre un error durante la búsqueda
     */
    public List<MaterialCalculoDTO> buscarPorTipo(TipoMaterialNegocio tipo) throws NegocioException {
        try {
            return MaterialCalculoMapper.toDTOList(materialCalculoDAO.buscarPorTipo(MaterialCalculoMapper.convertirTipoMaterial(tipo)));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar materiales por tipo: " + tipo, ex);
        }
    }

    /**
     * Busca materiales por su unidad base en la base de datos.
     *
     * @param unidad Unidad base de material a buscar
     * @return Lista de materiales con la unidad base especificada
     * @throws NegocioException Si ocurre un error durante la búsqueda
     */
    public List<MaterialCalculoDTO> buscarPorUnidadBase(UnidadMaterialNegocio unidad) throws NegocioException {
        try {
            return MaterialCalculoMapper.toDTOList(materialCalculoDAO.buscarPorUnidadBase(MaterialCalculoMapper.convertirUnidadMaterial(unidad)));
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar materiales por unidad base: " + unidad, ex);
        }
    }

    /**
     * Obtiene todos los materiales registrados en la base de datos.
     *
     * @return Lista de todos los materiales registrados
     * @throws NegocioException Si ocurre un error durante la consulta
     */
    public List<MaterialCalculoDTO> buscarTodos() throws NegocioException {
        try {
            return MaterialCalculoMapper.toDTOList(materialCalculoDAO.buscarTodos());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar todos los materiales", ex);
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
                // Para columnas cuadradas se usa alto * ancho * ancho (área de la sección * altura)
                volumen = elemento.getAlto() * elemento.getAncho() * elemento.getAncho();
                break;

            case LOSA_CONTRAPISO:
            case LOSA_ENTREPISO:
                // Para losas: largo * ancho * espesor
                volumen = elemento.getLargo() * elemento.getAncho() * elemento.getEspesor();
                break;

            case VIGA:
                // Para vigas: largo * ancho * espesor 
                volumen = elemento.getLargo() * elemento.getAncho() * elemento.getAlto();
                break;

            case MURO_LADRILLO:
                // Para muros: largo * alto * espesor
                volumen = elemento.getLargo() * elemento.getAlto() * elemento.getEspesor();
                break;

            case NIVELACION_MUROS_VERTICAL:
                // Para nivelación vertical: largo * alto * espesor
                volumen = elemento.getLargo() * elemento.getAlto() * elemento.getEspesor();
                break;

            case NIVELACION_PISOS_HORIZONTAL:
                // Para nivelación horizontal: largo * ancho * espesor
                volumen = elemento.getLargo() * elemento.getAncho() * elemento.getEspesor();
                break;

            default:
                volumen = 0.0;
        }

        return volumen;
    }

    /**
     * Calcula los materiales necesarios según el tipo de elemento y su volumen.
     *
     * @param elemento Elemento para el que se calcularán los materiales
     * @param volumen Volumen del elemento en metros cúbicos
     * @return Lista de materiales calculados
     */
    private List<MaterialCalculoDTO> calcularMaterialesSegunTipo(ElementoDTO elemento, Double volumen) {
        List<MaterialCalculoDTO> materiales = new ArrayList<>();

        switch (elemento.getTipo()) {
            case COLUMNA_CUADRADA:
            case VIGA:
                // Materiales para concreto reforzado
                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.CEMENTO, UnidadMaterialNegocio.KILOGRAMO, volumen * 350)); // 350 kg/m³
                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.ARENA, UnidadMaterialNegocio.METRO_CUBICO, volumen * 0.6)); // 0.6 m³/m³
                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.GRAVA, UnidadMaterialNegocio.METRO_CUBICO, volumen * 0.6)); // 0.6 m³/m³
                break;

            case LOSA_CONTRAPISO:
            case LOSA_ENTREPISO:
                // Materiales para losas
                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.CEMENTO, UnidadMaterialNegocio.KILOGRAMO, volumen * 300)); // 300 kg/m³
                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.ARENA, UnidadMaterialNegocio.METRO_CUBICO, volumen * 0.5)); // 0.5 m³/m³
                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.GRAVA, UnidadMaterialNegocio.METRO_CUBICO, volumen * 0.7)); // 0.7 m³/m³
                break;

            case MURO_LADRILLO:
                // Cálculo para muros de ladrillo
                Double areaMuro = elemento.getLargo() * elemento.getAlto();
                int cantidadLadrillos = (int) Math.ceil(areaMuro * 50); // Aproximadamente 50 ladrillos por m²
                Double volumenMortero = areaMuro * elemento.getEspesor() * 0.25; // 25% del volumen es mortero

                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.LADRILLO, UnidadMaterialNegocio.PIEZA, (double) cantidadLadrillos));
                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.CEMENTO, UnidadMaterialNegocio.KILOGRAMO, volumenMortero * 350)); // 350 kg/m³
                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.ARENA, UnidadMaterialNegocio.METRO_CUBICO, volumenMortero * 1.2)); // 1.2 m³/m³
                break;

            case NIVELACION_MUROS_VERTICAL:
                // Materiales para nivelación de muros
                Double areaNivelacion = elemento.getLargo() * elemento.getAlto();
                Double volumenMorteroNiv = areaNivelacion * elemento.getEspesor();

                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.CEMENTO, UnidadMaterialNegocio.KILOGRAMO, volumenMorteroNiv * 400)); // 400 kg/m³
                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.ARENA, UnidadMaterialNegocio.METRO_CUBICO, volumenMorteroNiv * 0.9)); // 0.9 m³/m³
                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.RESINA, UnidadMaterialNegocio.LITRO, areaNivelacion * 0.5)); // 0.5 L/m²
                break;

            case NIVELACION_PISOS_HORIZONTAL:
                // Materiales para nivelación de pisos
                Double areaPiso = elemento.getLargo() * elemento.getAncho();
                Double volumenMorteroPiso = areaPiso * elemento.getEspesor();

                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.CEMENTO, UnidadMaterialNegocio.KILOGRAMO, volumenMorteroPiso * 450)); // 450 kg/m³
                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.ARENA, UnidadMaterialNegocio.METRO_CUBICO, volumenMorteroPiso * 0.95)); // 0.95 m³/m³
                materiales.add(new MaterialCalculoDTO(TipoMaterialNegocio.RESINA, UnidadMaterialNegocio.LITRO, areaPiso * 0.4)); // 0.4 L/m²
                break;
        }

        return materiales;
    }

}
