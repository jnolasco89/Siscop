package sv.org.siscop.caritas.util;

/**
 *
 * @author Henry
 */
public enum Item {

    /* Tipos de persona*/
    NATURAL(1),
    JURIDICA(2);

    /**
     * Codigo de Item
     */
    private final Integer codigo;

    /**
     *
     * @param codigo que es el valor catalogo
     */
    private Item(Integer codigo) {
        this.codigo = codigo;
    }
    
    public Integer getCodigo() {
        return codigo;
    }

}
