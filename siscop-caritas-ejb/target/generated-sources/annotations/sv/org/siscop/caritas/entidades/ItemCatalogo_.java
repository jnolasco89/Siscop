package sv.org.siscop.caritas.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.org.siscop.caritas.entidades.Audit;
import sv.org.siscop.caritas.entidades.Catalogo;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-07T14:43:52")
@StaticMetamodel(ItemCatalogo.class)
public class ItemCatalogo_ { 

    public static volatile SingularAttribute<ItemCatalogo, String> descripcion;
    public static volatile SingularAttribute<ItemCatalogo, String> codigo;
    public static volatile SingularAttribute<ItemCatalogo, Boolean> estado;
    public static volatile SingularAttribute<ItemCatalogo, Audit> audit;
    public static volatile SingularAttribute<ItemCatalogo, Catalogo> catalogo;
    public static volatile SingularAttribute<ItemCatalogo, Integer> id;

}