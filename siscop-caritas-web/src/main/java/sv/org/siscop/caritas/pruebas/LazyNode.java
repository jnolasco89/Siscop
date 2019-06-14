/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.pruebas;

import java.util.List;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Nolasco
 */
public class LazyNode extends DefaultTreeNode {

    private boolean hijosCargados;

    public LazyNode(Object data, TreeNode parent) {
        super(data, parent);
        hijosCargados = false;
    }

    @Override
    public List<TreeNode> getChildren() {
        //ensureChildrenFetched();
        //System.out.println("NTES DE CRGR NODOS: "+super.getData().toString()+" "+super.isExpanded());
        cargarNodosHijos();
        return super.getChildren();
    }

    @Override
    public int getChildCount() {
        //ensureChildrenFetched();
        return super.getChildCount();
    }

    @Override
    public boolean isLeaf() {
        //ensureChildrenFetched();
        return super.isLeaf();
    }

    private void cargarNodosHijos() {
        System.out.println("NTES DE CRGR NODOS: " + super.getData().toString() + " " + super.isExpanded());
        if (!hijosCargados) {
            System.out.println("DESPUES DE CARGR HIJOS: " + super.isExpanded());
        }
    }
}
