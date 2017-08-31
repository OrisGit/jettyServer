package resource_sustem;

import java.util.HashMap;
import java.util.Map;

public class ResourceSystem {
    private static ResourceSystem instance;

    private Map<Class, Object> resources = new HashMap<Class, Object>();

    private ResourceSystem(){
        loadResource();
    }

    private void loadResource() {
        //TODO загрузка ресурсов
    }

    public static ResourceSystem getInstance() {
        if(instance==null){
            instance = new ResourceSystem();
        }
        return instance;
    }

    public Object getResource(Class clazz){
        return resources.get(clazz);
    }
}
