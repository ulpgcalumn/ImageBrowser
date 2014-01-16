package imagebrowser;

import imagebrowser.control.ImageCommand;
import imagebrowser.control.NextImageCommand;
import imagebrowser.control.PrevImageCommand;
import imagebrowser.model.Image;
import imagebrowser.persistence.ImageListLoader;
import imagebrowser.persistence.file.FileImageListLoader;
import imagebrowser.ui.swing.ActionListenerFactory;
import imagebrowser.ui.swing.ApplicationFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageBrowser {

    private ApplicationFrame frame;
    private Map<String, ImageCommand> commandMap;
    private static String path;

    public static void main(String[] args) {
        try {
            path = getPathFromParam(args);
        } catch (java.lang.Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }

        new ImageBrowser().execute();
    }

    private void execute() {
        ImageListLoader loader = createImageListLoader(path);
        List<Image> list = loader.load();
        frame = new ApplicationFrame(createActionListenerFactory());
        frame.getImageViewer().setImage(list.get(0));
        createCommands();
        frame.setVisible(true);
    }

    private void createCommands() {
        commandMap = new HashMap<>();
        commandMap.put("Next", new NextImageCommand(frame.getImageViewer()));
        commandMap.put("Prev", new PrevImageCommand(frame.getImageViewer()));
    }

    private ImageListLoader createImageListLoader(String path) {
        return new FileImageListLoader(path);
    }

    private ActionListenerFactory createActionListenerFactory() {
        return new ActionListenerFactory() {

            @Override
            public ActionListener create(final String action) {
                return new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        ImageCommand command = commandMap.get(action);
                        if (command == null)
                            return;
                        command.execute();
                    }
                };
            }
        };
    }

    private static String getPathFromParam(String[] args) throws java.lang.Exception {
        if (args.length < 1)
            throw new Exception("Path is missing");
        return args[0];
    }
}