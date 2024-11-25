package com.CourseDesign;
//主要运行
import com.CourseDesign.GUI.MainFrame;

import javax.swing.*;

public class MainRun {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
