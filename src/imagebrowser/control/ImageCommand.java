package imagebrowser.control;

import imagebrowser.ui.ImageViewer;

public abstract class ImageCommand {

    private final ImageViewer viewer;

    public ImageCommand(ImageViewer viewer) {
        this.viewer = viewer;
    }
    
    public ImageViewer getViewer() {
        return viewer;
    }
    
    public abstract void execute();
    
}