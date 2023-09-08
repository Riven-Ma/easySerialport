package com.liubike.view.support;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class SuperTextArea extends JPanel{
	
    private static final long serialVersionUID = 1L;

	private LinePanel linePanel;

    private JTextArea textArea;

    private JScrollPane scrollPane;

    public SuperTextArea() {
        this.setLayout(new BorderLayout());
        linePanel = new LinePanel();
        linePanel.setPreferredSize(new Dimension(25, 10));
        linePanel.setVisible(showLine);
        
        this.textArea = new JTextArea() {
            private static final long serialVersionUID = 2L;
			public void paint(Graphics g) {
                super.paint(g);
                linePanel.repaint();
            }
        };
        this.scrollPane = new JScrollPane(this.textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
       
        this.add(linePanel, BorderLayout.WEST);
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    
    
    public JTextArea getTextArea() {
		return textArea;
	}

    private Boolean showLine = false;
    

    public Boolean getShowLine() {
		return showLine;
	}

	public void setShowLine(Boolean showLine) {
		this.showLine = showLine;
		linePanel.setVisible(showLine);
	}



	// 内部类：显示行号的面板
    private class LinePanel extends JPanel {
        private static final long serialVersionUID = 1L;

		@Override
        public void paint(Graphics g) {
            super.paint(g);
            if(!showLine) {
            	return;
            }
            // starting pos in document
            int start = textArea.viewToModel(scrollPane.getViewport().getViewPosition());
            // end pos in doc
            int end = textArea.viewToModel(new Point(scrollPane.getViewport().getViewPosition().x + textArea.getWidth()
                    , scrollPane.getViewport().getViewPosition().y + textArea.getHeight()));

            // translate offsets to lines
            Document doc = textArea.getDocument();
            int startLine = doc.getDefaultRootElement().getElementIndex(start) + 1;
            int endLine = doc.getDefaultRootElement().getElementIndex(end) + 1;

            int fontHeight = g.getFontMetrics(textArea.getFont()).getHeight();
            int fontDesc = g.getFontMetrics(textArea.getFont()).getDescent();
            int starting_y = -1;
            
            try {
                starting_y = textArea.modelToView(start).y -
                        scrollPane.getViewport().getViewPosition().y + fontHeight - fontDesc;
            } catch (BadLocationException ble) {
                ble.printStackTrace();
            }

            for (int line = startLine, y = starting_y; line <= endLine; y += fontHeight, line++) {
                g.drawString(Integer.toString(line), getNumX(line), y);
            }
        }

        // 获取当前行的行号 x 坐标
        private int getNumX(int line) {
            int width = linePanel.getWidth() - 5;
            FontMetrics metrics = linePanel.getFontMetrics(this.getFont());
            int fontHeight = metrics.getHeight();
            int fontWidth = metrics.stringWidth("0");
            int digit = 0;
            while (line > 0) {
                line = line / 10;
                digit++;
            }
            int x = width - digit * fontWidth;
            // 自适应调整宽度
            if (x < 5) {
                linePanel.setPreferredSize(new Dimension(getWidth() + fontWidth, fontHeight));
                linePanel.updateUI();
            }
            return x;
        }
    }

}
