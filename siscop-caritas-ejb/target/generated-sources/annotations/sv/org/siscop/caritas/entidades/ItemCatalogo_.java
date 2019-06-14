package sv.org.siscop.caritas.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.org.siscop.caritas.entidades.Catalogo;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-14T09:57:56")
@StaticMetamodel(ItemCatalogo.class)
public class ItemCatalogo_ { 

    public static volatile SingularAttribute<ItemCatalogo, String> descripcion;
    public static volatile SingularAttribute<ItemCatalogo, Date> fechamod;
    public static volatile SingularAttribute<ItemCatalogo, String> usercrea;
    public static volatile SingularAttribute<ItemCatalogo, Catalogo> idcatalogo;
    public static volatile SingularAttribute<ItemCatalogo, Date> fechacrea;
    public static volatile SingularAttribute<ItemCatalogo, Integer> id;
    public static volatile SingularAttribute<ItemCatalogo, String> usermod;
    public static volatile SingularAttribute<ItemCatalogo, Boolean> activo;

}