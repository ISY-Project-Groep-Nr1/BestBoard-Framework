# BestBoard framework
the best framework (hell yeah) (tevreden RUBEN?) (Ja hoor, ziet er geweldig uit!)


# Documentation

# Layers
The core of the framework works 
![story dependency chart(1).png](../../Downloads/story%20dependency%20chart%281%29.png)



# Graphics
The graphics are deeply entrenched in the Layer system.<br>
With every gui element and Drawable being elements in layers

## Gui elements

### BestWindow
BestWindow is a wrapper of JFrame, but is also responsible for the drawing of the gui elements on the screen.<br>
BestWindow is a singleton, so only one window can exist at the same time, but it is also easily obtainable via 

    BestWindow.get()    
    
To create a new window call

    BestWindow.create(layerManager: LayerManager, title: String)

Where layerManager contains (or will contain) all gui elements and title is the title of the JFrame
When gui changes are made to the gui, the window can be updated via bestWindow.update(). <br>

Which causes the frame to Automatically reflect all JComponent, BestGuiElement and GuiRepresentable. <br>

To change the order at which layers are drawn,
set the persistentVariable RENDER_PRIORITY_KEY to a negative int to priority it.<br>

To make modifications to the Container on which is drawn, or change the Container entirely, 
you can use the persistentVariable FRAME_PREPARER_KEY, of type Function<JComponent, JComponent>.
If multiple FRAME_PREPARERS are specified, only the one in the layer with the highest priority is used.

#### Component configurers
If, for instance, the panel you use is of type splitPane, you can't just use pane.add(component). <br>
To Account for this ComponentConfigurers which is responsible for adding the give component to the given container. <br>
ComponentConfigurers can be configured in a BesGuiElement, with the setComponentConfigurer method. 
Or with the DEFAULT_CONFIGURER_KEY for a layer,
which enables it as default for every element in the layer.<br>
Example:
    
    BestButton b = new BestButton(...).setConfigurer((parent, child) -> {
        parent.add(child);
    });


### BestButton
A button with a custom [style](#style), [font size, font type](#Font), and onClick Runnable. <br>

### BestLabel
A label with a custom [style](#style), [font size and font type](#Font). <br>

### BestPopUp
A popup, used for things like confirmation boxes.
It Has its own LayerManager elements drawn on this popUp.
Look at TicTacToeGuiLayer for an example.

### BestCanvas
The element responsible for drawing all drawables.
Draws all drawables inside the passed in LayerManager, every time refresh is called
Has a fixed width and height.
All drawables are drawn relative to this element.
Only one BestCanvas can exist at a time, having a second one in your layers results in a crash.

## Style
Style is an interface responsible for the visuals of the framework.<br>
This includes, drawing panels, drawing text, background color, choosing fonts and drawing buttons.

#### Font
Some styles need different sizes of fonts than other styles. 
To help abstract this from the GuiElements and users, size is instead specified in Sizes: large, medium and small.
