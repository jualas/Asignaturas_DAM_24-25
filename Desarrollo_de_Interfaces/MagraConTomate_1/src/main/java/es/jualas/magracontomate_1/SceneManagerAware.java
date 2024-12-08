package es.jualas.magracontomate_1;

public interface SceneManagerAware {
    void setSceneManager(SceneManager sceneManager);
    default void onSceneDisplayed() {} // Método por defecto, opcional de implementar
}