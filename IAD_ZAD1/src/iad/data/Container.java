/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad.data;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author glabg
 */
public class Container {

    private static Container instance = null;
    private List<Entity> collection;
    private Set<String> classes = new HashSet<>();

    private Container() {
        collection = new LinkedList<>();
    }

    public static Container getInstance() {
        if (instance == null) {
            instance = new Container();
        }
        return instance;
    }

    public void addEntity(Entity entity) {
        classes.add(entity.getKlass());
        collection.add((Entity) entity);
    }

    public List getEntities() {
        return collection;
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public void clear() {
        collection = new LinkedList<>();
        classes.clear();
    }

    public Set<String> getClasses() {
        return classes;
    }

    public int getNubmerOfParamters() {
        return collection.get(0).getParams().size();
    }
}
