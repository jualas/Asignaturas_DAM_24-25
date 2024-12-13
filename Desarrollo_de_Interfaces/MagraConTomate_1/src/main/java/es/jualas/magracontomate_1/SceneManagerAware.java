package es.jualas.magracontomate_1;

public interface SceneManagerAware {
    // Método para establecer el SceneManager en la clase que implemente esta interfaz
    void setSceneManager(SceneManager sceneManager);

    // Método opcional que se llama cuando la escena se muestra
    default void onSceneDisplayed() {}
}
