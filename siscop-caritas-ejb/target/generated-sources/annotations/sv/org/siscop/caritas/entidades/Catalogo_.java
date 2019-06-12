package sv.org.siscop.caritas.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sv.org.siscop.caritas.entidades.Audit;
import sv.org.siscop.caritas.entidades.ItemCatalogo;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-11T08:24:57")
@StaticMetamodel(Catalogo.class)
public class Catalogo_ { 

    public static volatile SingularAttribute<Catalogo, String> codigo;
    public static volatile SingularAttribute<Catalogo, Boolean> estado;
    public static volatile SingularAttribute<Catalogo, Audit> audit;
    public static volatile SingularAttribute<Catalogo, Integer> id;
    public static volatile ListAttribute<Catalogo, ItemCatalogo> itemCatalogoList;
    public static volatile SingularAttribute<Catalogo, String> nombre;

}