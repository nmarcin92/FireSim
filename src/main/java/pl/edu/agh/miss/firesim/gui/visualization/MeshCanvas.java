package pl.edu.agh.miss.firesim.gui.visualization;

import com.google.common.collect.*;

import java.awt.*;

/**
 * @author mnowak
 */
public class MeshCanvas extends Canvas {

    private final Table<Integer, Integer, Color> colorTable;
    private final int sizeX, sizeY;
    private int dx, dy;
    private int offsetX, offsetY;

    public MeshCanvas(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        colorTable = ArrayTable.create(ContiguousSet.create(Range.closed(0, sizeX), DiscreteDomain.integers()).asList(),
                ContiguousSet.create(Range.closed(0, sizeY), DiscreteDomain.integers()).asList());
    }

    public Table<Integer, Integer, Color> getColorTable() {
        return colorTable;
    }

    @Override
    public void paint(Graphics g) {
        createBufferStrategy(2);
        Graphics2D g2d = (Graphics2D) g;

        dx = Math.round((float) getWidth() / sizeX);
        dy = Math.round((float) getHeight() / sizeY);

        offsetX = Math.round(0.05f * dx);
        offsetY = Math.round(0.05f * dy);

        for (int x = 0; x < sizeX + 1; ++x) {
            g2d.setColor(Color.BLACK);
            g2d.drawLine(x * dx, 0, x * dx, getHeight());
        }

        for (int y = 0; y < sizeY + 1; ++y) {
            g2d.setColor(Color.BLACK);
            g2d.drawLine(0, y * dy, getWidth(), y * dy);
        }

    }

    @Override
    public void update(Graphics g) {
        Graphics2D g2d = (Graphics2D)
                getBufferStrategy().getDrawGraphics();


        for (Table.Cell<Integer, Integer, Color> cell : colorTable.cellSet()) {
            g2d.setColor(cell.getValue());
            g2d.fillRect(cell.getColumnKey() * dx + offsetX,
                    cell.getRowKey() * dy + offsetY,
                    dx - 2 * offsetX, dy - 2 * offsetY);
        }

        getBufferStrategy().show();
    }


}