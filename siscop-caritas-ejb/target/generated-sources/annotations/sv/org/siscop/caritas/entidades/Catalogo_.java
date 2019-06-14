package sv.org.siscop.caritas.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.org.siscop.caritas.entidades.ItemCatalogo;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-14T09:57:56")
@StaticMetamodel(Catalogo.class)
public class Catalogo_ { 

    public static volatile SingularAttribute<Catalogo, Date> fechamod;
    public static volatile SingularAttribute<Catalogo, String> usercrea;
    public static volatile SingularAttribute<Catalogo, Date> fechacrea;
    public static volatile SingularAttribute<Catalogo, Integer> id;
    public static volatile SingularAttribute<Catalogo, String> usermod;
    public static volatile ListAttribute<Catalogo, ItemCatalogo> itemCatalogoList;
    public static volatile SingularAttribute<Catalogo, String> nombre;
    public static volatile SingularAttribute<Catalogo, Boolean> activo;

}