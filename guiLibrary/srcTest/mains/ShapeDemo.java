/*package mains;

import base.FSwing;
import base.Slot;
import base.Types;
import base.FSwing.Button;
import base.FluentGUI;
import base.Types.*;

public class ShapeDemo {
  private Color rectColor   = new Color(new Red(255), new Green(100), new Blue(100));
  private Color ovalColor   = new Color(new Red(100), new Green(200), new Blue(255));
  private Color lineColor   = new Color(new Red(255), new Green(255), new Blue(100));
  private float lineY       = 0.75f;
  private KeyStroke upKey   = new KeyStroke("UP");
  private KeyStroke downKey = new KeyStroke("DOWN");

  private final Slot<FSwing.Button> toggleBtn = Slot.of();

  private void updateRectColor(Color c) { rectColor = c; }
  private void updateOvalColor(Color c) { ovalColor = c; }
  private void updateLineY(float delta) { lineY = Math.max(0f, Math.min(1f, lineY + delta)); }

  private void toggleKeys() {
    upKey   = upKey.k().equals("UP")            ? new KeyStroke("W")  : new KeyStroke("UP");
    downKey = downKey.k().equals("DOWN")        ? new KeyStroke("S")  : new KeyStroke("DOWN");
    toggleBtn.get().text(upKey.k().equals("UP") ? "Keys: Arrows"      : "Keys: WASD");
    }

  private void run() {
    FluentGUIResult result = new FluentGUI().run(frame -> frame
      .title("Shape Demo")
      .fps(new FPS(60))
      .resizable()
      .maximized()
      .undecorated(new Opacity(0.8f))
      .onKey(k -> k
        .pressed(() -> new KeyStroke("R"), _ -> updateRectColor(new Color(new Red(255), new Green(0), new Blue(0))))
        .pressed(() -> new KeyStroke("G"), _ -> updateRectColor(new Color(new Red(0), new Green(255), new Blue(0))))
        .pressed(() -> upKey, _ -> updateLineY(-0.05f))
        .pressed(() -> downKey, _ -> updateLineY(0.05f))
        )
      .flow(panel -> panel
        .transparent()
        .button("Crash", () -> { throw new RuntimeException("Explode"); }, Slot.of())
        .button("Keys: Arrows", this::toggleKeys, toggleBtn)
        .label("Hello World!", Slot.of())
        .label("Foo Bar Baz", Slot.of())
        .size(new Dimension(new Width(800), new Height(800)))
        .background(new Color(new Red(30), new Green(30), new Blue(30)))
        .onMouse(m -> m
          .clicked(_ -> updateOvalColor(new Color(
            new Red((int) (Math.random() * 255)), 
            new Green((int) (Math.random() * 255)), 
            new Blue((int) (Math.random() * 255))
            )))
          )
        .paint(ctx -> {
          var s = ctx.panelSize().toVector2();
          int rectX = (int) (s.x() * 0.10);
          int rectY = (int) (s.y() * 0.10);
          Dimension rectDim = new Dimension(s.mul(0.25));
          int ovalX = (int) (s.x() * 0.40);
          int ovalY = (int) (s.y() * 0.40);
          Dimension ovalDim = new Dimension(s.mul(0.20));
          int lineYPos = (int) (s.y() * lineY);
          int lineXEnd = (int) (s.x() * 0.90);

          ctx.color(rectColor)
           .position(new X(rectX), new Y(rectY))
           .rect(rectDim)
           .color(ovalColor)
           .position(new X(ovalX), new Y(ovalY))
           .oval(ovalDim)
           .color(lineColor)
           .position(new X(rectX), new Y(lineYPos))
           .line(new X(lineXEnd), new Y(lineYPos));
          })
        )
      );

    System.out.println(switch(result) {
      case FluentGUIResult.Unknown() -> "Unknown";
      case FluentGUIResult.Closed() -> "Closed";
      case FluentGUIResult.Crashed(RuntimeException cause) -> "Crashed with error: " + cause.getLocalizedMessage();
      });
    }

  public static void main(String[] args) {
    new ShapeDemo().run();
    }
  }
*/