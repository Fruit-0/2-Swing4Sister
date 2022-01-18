package org.example;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class Swing4Sister {

    private static int excelNumber = 0;

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("王星是个好同志1.0");
        //Toolkit是抽象类，不能使用new Toolkit() 创建实例化对象，须通过静态方法getDefaultToolkit() 来获取Toolkit对象
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        //获取屏幕的大小。 在具有多个显示器的系统上，使用主显示器
        Dimension screenSize = defaultToolkit.getScreenSize();
        // 获取屏幕的宽
        int screenWidth = screenSize.width;
        //获取屏幕的高
        int screenHeight = screenSize.height;

        //x–该组件的新x坐标；y–该组件的新y坐标；width–该组件的新width；height–该组件的新height
        jFrame.setBounds((screenWidth/2)-(800/2), (screenHeight/2)-(600/2),
                800, 300);

        //构建窗口
        buildJFrame(jFrame);
        //设置点击关闭按钮后，自动结束进程
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setVisible(true);方法的意思是说数据模型已经构造好了，允许JVM可以根据数据模型执行paint方法开始画图并显示到屏幕上了，并不是显示图形，而是可以运行开始画图了。
        jFrame.setVisible(true);
    }


    /**
     * @Description: 构建窗口
     * @Author: YX-WJ
     * @Date: 2022/1/16 19:17
     * @param jFrame:
     * @return: void
     **/
    private static void buildJFrame(JFrame jFrame){
        //在窗口中装载面板
        //jFrame.add(jPanel, BorderLayout.CENTER);

        //创建菜单栏-----------------------------------------------START
        ////菜单栏对象
        //JMenuBar jMenuBar = new JMenuBar();
        ////菜单名称
        //JMenu jMenu = new JMenu("excel处理");
        ////一级菜单
        //JMenu jMenuItemSbu = new JMenu("点位处理");
        //jMenu.add(jMenuItemSbu);
        //jMenuBar.add(jMenu);
        //
        //// ButtonGroup for radio buttons
        //ButtonGroup directionGroup = new ButtonGroup();
        //
        //// Edit->Options->Forward, F - Mnemonic, in group
        //JRadioButtonMenuItem forwardMenuItem = new JRadioButtonMenuItem("点位处理（初级）", true);
        //forwardMenuItem.setMnemonic(KeyEvent.VK_F);
        //jMenuItemSbu.add(forwardMenuItem);
        //directionGroup.add(forwardMenuItem);
        //
        //// Edit->Options->Backward, B - Mnemonic, in group
        //JRadioButtonMenuItem backwardMenuItem = new JRadioButtonMenuItem("点位处理（高级）");
        //backwardMenuItem.setMnemonic(KeyEvent.VK_B);
        //jMenuItemSbu.add(backwardMenuItem);
        //directionGroup.add(backwardMenuItem);
        //
        //jFrame.setJMenuBar(jMenuBar);
        //创建菜单栏-----------------------------------------------END

        JPanel jPanel = new JPanel();

        //添加一个标签
        JLabel label1 = new JLabel();
        label1.setBounds(34,49,53,18);
        label1.setText("点击按钮开始执行脚本");
        label1.setToolTipText("JLabel");
        jPanel.add(label1);


        //添加一个按钮
        JButton button1 = new JButton();
        button1.setBounds(103,110,71,27);
        button1.setText("选择要处理的excel文件夹");
        button1.setToolTipText("选择要处理的excel文件夹，不得包含无关文件！");
        button1.addActionListener(new HelloButton());//添加监听器类,其主要的响应都由监听器类的方法实现

        jPanel.add(button1);


        //jPanel.setBackground(Color.blue);
        jFrame.add(jPanel);




    }


    /**
     * 监听器类实现ActionListener接口，主要实现actionPerformed方法
     * @author HZ20232
     *
     */
    private static class HelloButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try {
                doInCmd();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"未知的错误发生了，请联系管理员","执行错误",JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    /**
     * @Description: 在cmd窗口中运行 @Author: YX-WJ @Date: 2022/1/10 0:07
     *
     * @return: void
     */
    private static void doInCmd() throws Exception {
        // 文件选择器
        JFileChooser jfc = new JFileChooser();

        // 选择目录
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // 一个文件（也是默认的值）
        // jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // 设置可选多个文件
        // jfc.setMultiSelectionEnabled(true);

        // JfileChooser.Cancel_Option:取消
        // JfileChooser.Approve_Option:打开
        // JfileChooser.Error_Option:发生错误
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            // 可单选也可多选
            String excelFilesPath = jfc.getSelectedFile().getPath();
            String txtPath = "";
            // 太快了老姐反应不过来
            Thread.sleep(500);
            JOptionPane.showMessageDialog(null,"请继续选择","请继续选择txt文件目录",JOptionPane.INFORMATION_MESSAGE);
            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                txtPath = jfc.getSelectedFile().getPath();
            }

            String txtFileName =
                    txtPath + "\\" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "-数据.txt";
            dealExcels(excelFilesPath, txtFileName);
            JOptionPane.showMessageDialog(null,"执行完毕,请直接关闭窗口退出程序","执行完毕",JOptionPane.INFORMATION_MESSAGE);

            //            遍历多选的文件
      /*            File[] selectedFiles = jfc.getSelectedFiles();
      for (File selectedFile : selectedFiles) {
          System.out.println(selectedFile.getName());
      }*/
        } else {
            System.out.println("No file is selected!");
        }


    }

    /**
     * @param excelFilesPath:
     * @param txtPath: @Description: 处理文件夹中的所有excel @Author: YX-WJ @Date: 2022/1/9 23:12
     * @return: void
     */
    private static void dealExcels(String excelFilesPath, String txtPath) throws Exception {
        File excelirectory = new File(excelFilesPath);
        if (excelirectory.isDirectory()) {
            File[] files = excelirectory.listFiles();
            // 每次执行都重新创建txt文件
            if (new File(txtPath).exists()) {
                new File(txtPath).delete();
            }
            // 遍历excel文件夹下的文件
            for (File file : files) {
                if (file.isFile()) {
                    excelNumber+=1;
                    // 写数据
                    writeExcelInfoTxt(file.getPath(), txtPath);
                }
            }
        } else {
            throw new Exception("请指定一个文件夹");
        }
    }


    /**
     * @param excelPath:
     * @param txtPath: @Description: 读取excel的信息并写入txt @Author: YX-WJ @Date: 2022/1/9 22:47
     * @return: void
     */
    public static void writeExcelInfoTxt(String excelPath, String txtPath) throws Exception {

        // 要写入txt的内容
        String valueString = "";
        // txt文件中的基本内容(只有文件为空的时候才会进行写入基本内容操作)
        if (new File(txtPath).length() == 0) {
            String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            valueString +=
                    "[属性描述]\n"
                            + "格式版本号=\n"
                            + "数据产生单位=国土资源部\n"
                            + "数据产生日期="
                            + dateFormat
                            + "\n"
                            + "坐标系=2000国家大地坐标系\n"
                            + "几度分带=3\n"
                            + "投影类型=高斯克吕格\n"
                            + "计量单位=米\n"
                            + "带号=39\n"
                            + "精度=0.01\n"
                            + "转换参数=0,0,0,0,0,0,0\n"
                            + "[地块坐标]\n";
        }

        // 【宗地面积】和【总点数】要替换一次
        valueString += "【总点数】,【宗地面积】,1,地块"+excelNumber+",面,,公共管理与公共服务用地,,@\n";

        DataFormatter dataFormatter = new DataFormatter();
        // 创建工作簿
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(excelPath));
        // 读取第一个工作表(这里的下标与list一样的，从0开始取，row从1开始取，cell从0开始取)
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

        // 【总点数】
        int biggestRowNum = 0;
        // 【宗地面积】txt中单位是平方千米，excel中单位是平方米，需要转换
        String arec = "";

        // 默认只检测前五行
        for (int i = 0; i < 5; i++) {
            XSSFRow row = sheet.getRow(i);
            String cellValue = dataFormatter.formatCellValue(row.getCell(0));
            if (cellValue.contains("宗地面积")) {
                arec = cellValue.replaceAll("[^0-9&^.]", "");
                // 保留小数点后4位，四舍五入
                arec =new BigDecimal(arec)
                        .divide(new BigDecimal(10000), 4, BigDecimal.ROUND_HALF_UP)
                        .toString();
                break;
            }
        }
        // 【第一个点的信息】
        String firstStringInfo = "";
        // 普通点的信息
        String normalpointinfo = "";

        // 点号
        String pointNo = "";
        // X坐标
        String Xvalue = "";
        // Y坐标
        String Yvalue = "";

        // 遍历sheet页中的所有行
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            // 获取当前行对象
            XSSFRow row = sheet.getRow(i);
            // 获取当前行的第一个单元格，用于判断一共有多少的 点
            XSSFCell cell = row.getCell(0);
            // A1 获取单元格坐标
            // String s = new CellReference(row.getRowNum(), cell.getColumnIndex()).formatAsString();
            // 单元格类型为number时，code为0，非0的数据是文本，没有取值意义
            int code = cell.getCellType().getCode();
            if (code == 0) {
                // 获取到单元格的值
                String cellValue = dataFormatter.formatCellValue(cell);
                int cellValueInt = Integer.parseInt(cellValue);
                // 对于非空的单元格，比较一下是不是最大的点数
                if (!"".equals(cellValue)) {
                    if (biggestRowNum < cellValueInt) {
                        // 更新最大点数
                        biggestRowNum = cellValueInt;

                        // 点号
                        pointNo = dataFormatter.formatCellValue(row.getCell(1));
                        // x坐标
                        Xvalue = dataFormatter.formatCellValue(row.getCell(2));
                        // y坐标
                        Yvalue = dataFormatter.formatCellValue(row.getCell(3));
                        normalpointinfo = "" + pointNo + ",1," + Xvalue + "," + Yvalue + "\n";

                        valueString += normalpointinfo;
                        // 如果是第一行，还需要记录下来用来写到末尾
                        if (biggestRowNum == 1) {
                            // 记录第一行的内容，用于写入txt的最后一行
                            if (biggestRowNum == 1) {
                                firstStringInfo = normalpointinfo;
                            }
                        }
                    }
                }
            }
        }
        // 将第一行的数据写在末尾
        valueString += firstStringInfo;
        // 【总点数】,【宗地面积】
        valueString = valueString.replace("【总点数】", String.valueOf(biggestRowNum));
        valueString = valueString.replace("【宗地面积】", arec);
        writeInfo(txtPath, valueString);
    }

    /**
     * @param txtFile2Output:
     * @param txtString: @Description: 输出文件内容 @Author: YX-WJ @Date: 2022/1/9 22:37
     * @return: void
     */
    private static void writeInfo(String txtFile2Output, String txtString) throws IOException {
        File file = new File(txtFile2Output);
        // 创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        bufferedWriter.write(txtString);
        bufferedWriter.close();
    }


}
