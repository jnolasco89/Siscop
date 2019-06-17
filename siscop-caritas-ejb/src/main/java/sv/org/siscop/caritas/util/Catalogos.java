package sv.org.siscop.caritas.util;

/**
 *
 * @author Henry
 */
public enum Catalogos {

    /* Tipos de servicio*/
    TIPO_PERSON(1),
    ESTADO_CIVIL(2),
    TIPO_DIRECC(3),
    TIPO_DOC(4),
    TIPO_TEL(5);

    /**
     * iD de Catalogo
     */
    private final Integer codigo;

    /**
     * Constructor de la instancia
     *
     * @param codigo que es el valor catalogo
     */
    private Catalogos(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * Accesor del codigo del catalogo.
     *
     * @return el valor de la instancia
     */
    public Integer getCodigo() {
        return codigo;
    }

}
