/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BUS.AccountBUS;
import BUS.Common;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.LoginBUS;
import BUS.NhanVienBUS;
import BUS.SanPhamBUS;
import DAO.AccountDAO;
import DAO.KhachHangDAO;
import DAO.LoginDAO;
import DTO.ProductsDTO;
import DAO.MySQLConnect;
import DTO.AccountDTO;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.LoginDTO;
import DTO.NhanVienDTO;
import java.awt.*;
import static java.awt.Font.BOLD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import ExcelAndReport.*;
import com.toedter.calendar.JDateChooser;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;

/**
 *
 * @author nguyen thanh sang
 */
public class HomeUser extends JFrame implements MouseListener, ActionListener ,KeyListener{
    JDateChooser ngayBatDauDate,ngayKetThucDate;
    JPanel menuPanel,headerPanel,contentPanel,sanPhamPanel, iconPanel = new JPanel(),ptaikhoan,pnhanvien,psanPham,pkhachhang;
    JTable productTable,accountTabel,nhanvienTable,gioHangTable,hoaDonTable,thongKeHDTable,thongKeHDTongTienTheoQuy,khachhangtable;
    JLabel iconPizza,menuLabel[],taikhoanLabel,matkhauLabel,idLabel,typeLabel,hotenLabel,ngaysinhLabel,
            idnhanvienLabel,gioitinhLabel,diachiLabel,sdtLabel,tongTienTheoNgayLabel, tongTienLabel;
    JTextField countTXT, seachtxt,menuTextField[],menuTextFieldADMIN[],giaTriDauTXT,giaTriCuoiTXT,tongTienTXT, taikhoan_TextField, matkhau_TextField, id_TextField,type_TextField,
    idnhanvien_TextField,hoten_TextField,ngaysinh_TextField,gioitinh_TextField,diachi_TextField,sdt_TextField,gmail_TextField,hotenkh_TextField,sdtkh_TextField,idkh_TextField;
    static JButton refresh;
    JButton menuButton[],find, suataikhoanButton,themtaikhoanButton,xoataikhoanButton,suataikhoan1Button,themtaikhoan1Button,xoataikhoan1Button,QLNVExelButton,QLTKExelButton;
    JComboBox loaiSanPhamCBB;
    DefaultTableModel model = new DefaultTableModel();
    static DefaultTableModel modelGioHang = new DefaultTableModel();
    DefaultTableModel modelTaiKhoan = new DefaultTableModel();
    DefaultTableModel modelNhanvien = new DefaultTableModel();
    DefaultTableModel modelKhachhang = new DefaultTableModel();
    DefaultTableModel modelThongKeHD = new DefaultTableModel();
    DefaultTableModel modelHoadon = new DefaultTableModel();
    DefaultTableModel modelthongKeHDTongTienTheoQuy = new DefaultTableModel();
    Vector headerSP,headerGH,headerTK,dataTK,headerNV,dataNV,headerKH,dataKH;
    JScrollPane scrollPanel,scrollPanel1;  
    ProductsDTO Products = new ProductsDTO();
    LoginDTO login = new LoginDTO();
    LoginBUS login_bus = new LoginBUS();
    AccountDTO account = new AccountDTO();
    AccountBUS account_bus = new AccountBUS();
    KhachHangDTO khachhang = new KhachHangDTO();
    KhachHangBUS khachhang_bus = new KhachHangBUS();
    
    public static ArrayList<ProductsDTO> Arr_GioHang = new ArrayList();
    int count=1 , check=0;
    double Tong = 0.0,TongTienTheoNgay = 0.0;
    Date date = Date.valueOf(LocalDate.now());
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    

    public HomeUser() 
    {
        init();
        //Lay DU lieu ra (VINH)
        SanPhamBUS bus = new SanPhamBUS();
        if (SanPhamBUS.Arr_products.size() == 0) {
            try {
                bus.docSanPham();
            } catch (Exception ex) {

            }
        }
        Add_header("Mã sản phẩm","Tên sản phẩm","Đơn giá(VNĐ)","Loại Sản Phẩm");
        for (ProductsDTO products : SanPhamBUS.Arr_products) {
                Add_row_SanPham(products);
        }
        productTable.setModel(model);
    }
    public void init()
    {
        Font font = new Font("Segoe UI",Font.BOLD,14);
        setSize(1280,700);
        setLayout(new BorderLayout(3,3));
        
        //if(LoginBUS.Arr_login.get(login.getType()).equals(1)){
            menuPanel=CreateJPanel_Menu();
            menuPanel.setPreferredSize(new Dimension(200, 1000));
        //}
//        if(LoginBUS.Arr_login.get(login.getType()).equals(0)){
//            menuPanel=CreateJPanel_Menu_TYPE_0();
//            menuPanel.setPreferredSize(new Dimension(200, 1000));
//        }

        
        headerPanel= CreateJPanel_Header();
        headerPanel.setPreferredSize(new Dimension(0,50));
        
        contentPanel=new JPanel();      
        contentPanel = CreateJPanel_TrangChu();
        
        add(menuPanel,BorderLayout.WEST);
        add(headerPanel,BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    public JPanel CreateJPanel_Menu(){
        JPanel pMenu = new JPanel();
        pMenu.setLayout(null);
        pMenu.setBackground(Color.pink);           
        Border menuTitleBoder = BorderFactory.createTitledBorder("Menu");
        pMenu.setBorder(menuTitleBoder);
        if(Login.idhienhanh.equals("ADMIN")){
            JLabel iconHeader = CreateJLable_Icon(10, 20, 180, 200, "/Image/admin.PNG");
            pMenu.add(iconHeader);  
            menuButton=new JButton[8];
            String[] arrMenuAdmin = {"Quản Lí Sản Phẩm","Quản Lí Nhân Viên", "Quản Lí Tài Khoản","Quản Lí Khách Hàng",
                "Quản Lí Hoá Đơn","Thống Kê","Đăng xuất","Thoát"} ;
            int toaDoXMenuButton=5,toaDoYMenuButton=230;
            for(int i=0;i<arrMenuAdmin.length; i++)
            {
                menuButton [i] = new JButton(arrMenuAdmin[i]);
                menuButton[i].setBounds(toaDoXMenuButton, toaDoYMenuButton, 190, 50);
                menuButton[i].setFont(new Font("Arial", Font.PLAIN, 16));
                menuButton[i].setActionCommand("buttonAdmin_"+i);
                menuButton[i].addActionListener(this);
                menuButton[i].setBackground(new Color(255, 190, 133));
                //menuButton[i].setForeground(Color.CYAN);
                pMenu.add(menuButton[i]);
                toaDoYMenuButton += 50;
            }
        }else{
            JLabel iconHeader = CreateJLable_Icon(10, 20, 180, 200, "/Image/user.png");
            pMenu.add(iconHeader); 
            menuButton=new JButton[5];
            String[] arrMenu = {"Trang Chủ","Bán Hàng", "Tài Khoản","Đăng Xuất","Thoát"} ; 
            int toaDoXMenuButton=5,toaDoYMenuButton=230;
             for(int i=0;i<arrMenu.length; i++)     
            {
                menuButton [i] = new JButton(arrMenu[i]);
                menuButton[i].setHorizontalAlignment(menuButton[i].LEFT);
                menuButton[i].setBounds(toaDoXMenuButton, toaDoYMenuButton, 190, 50);
                menuButton[i].setFont(new Font("Arial", Font.PLAIN, 16));
                menuButton[i].setActionCommand("buttonStaff_"+i);
                menuButton[i].addActionListener(this);
                menuButton[i].setBackground(new Color(255, 190, 133));
//                menuButton[i].setHorizontalAlignment(RIGHT_ALIGNMENT);
                //menuButton[i].setForeground(Color.CYAN);
                pMenu.add(menuButton[i]);
                toaDoYMenuButton += 50;
            }
            menuButton[0].setIcon(new ImageIcon("./src/Image/home1.PNG"));
            menuButton[1].setIcon(new ImageIcon("./src/Image/pizza1.png"));
            menuButton[2].setIcon(new ImageIcon("./src/Image/account1.png"));
            menuButton[3].setIcon(new ImageIcon("./src/Image/logout1.PNG"));
            menuButton[4].setIcon(new ImageIcon("./src/Image/exit1.png"));
        }
        
        return pMenu;
    }

    public JLabel CreateJLable_Icon(int x , int y , int w , int h , String imgpath){
        Icon icon  = new ImageIcon( getClass().getResource(imgpath) );
        JLabel iconlable = new JLabel(icon);
        iconlable.setBounds(x, y, w,h);
        return iconlable;
    }
    
    public JPanel CreateJPanel_Header (){
        JPanel pHeader = new JPanel(null);
        pHeader.setBackground(Color.pink);

        JLabel title = new JLabel("Pizza Hups");
        title.setBounds(80, 10, 300, 30);
        title.setFont(new Font("Arial", 0, 20));
        title.setForeground(Color.black);
        JLabel logo = new JLabel(new ImageIcon("./src/Image/banhang.png"));
        logo.setBounds(-50, 0, 200, 48);
        pHeader.add(logo);
        pHeader.add(title);

        JLabel minimize = new JLabel(new ImageIcon("./src/Image/minimize.png"), JLabel.LEFT);
        minimize.setBounds(1180, 0, 60, 50);
        minimize.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                minimize.setBackground(Color.yellow);
            }

            public void mousePressed(MouseEvent evt) {
                setState(JFrame.ICONIFIED);
                minimize.setBackground(Color.yellow);
            }
        });
        pHeader.add(minimize);

        JLabel exit = new JLabel(new ImageIcon("./src/Image/close.png"), JLabel.LEFT);
        exit.setBounds(1230, 0, 60, 50);
        exit.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                exit.setBackground(Color.red);
            }

            public void mousePressed(MouseEvent evt) {
                dispose();
            }
        });
        pHeader.add(exit);
        this.setUndecorated(true);
        return pHeader;

    }
    
    public JPanel CreateJPanel_TrangChu (){
        JPanel trangChuPanel = new JPanel();
        trangChuPanel.setBackground(Color.PINK);
        trangChuPanel.setLayout(null);
        trangChuPanel.setBounds(0, 0, 1080, 770);
        JLabel chao = CreateJLable_Icon(-60,-70, 1200, 786, "/Image/backgroudpizza.jpg");
        chao.setFont(new Font("Arial", BOLD, 80));
        trangChuPanel.add(chao);
        this.setVisible(true);
        return trangChuPanel;
    }
    
    //TYPE = 0;
    public JPanel CreateJPanel_SanphamTable() {
        JPanel psanPham = new JPanel();
        psanPham.setLayout(null);
        psanPham.setBackground(Color.GRAY);
        //add tim kiem theo gia
        JPanel timKiemTheoGiaPanel = CreatePanel_TimKiemTheoGia();
        psanPham.add(timKiemTheoGiaPanel);
        //add tim kiem theo loai sp
        JPanel timKiemTheoLoaiPanel = CreatePanel_TimKiemLoai();
        psanPham.add(timKiemTheoLoaiPanel);

        //--------- Main table 
        headerSP = new Vector();
        headerSP.add("Mã sản phẩm");
        headerSP.add("Tên sản phẩm");
        headerSP.add("Đơn giá(VNĐ)");
        headerSP.add("Loại Sản Phẩm");
        if (model.getRowCount() == 0) {
            model = new DefaultTableModel(headerSP, 0);
        }
        productTable = new JTable(null, headerSP);
        psanPham.setBounds(0, 0, 1080, 660);
        productTable.setFont(new Font("Arial", 0, 15));
        productTable.setModel(model);//add model len table
        productTable.getTableHeader().setFont(new Font("Arial", BOLD, 18)); //set font cho vector header
        productTable.getTableHeader().setForeground(Color.black); //set màu chữ cho header
        productTable.getTableHeader().setPreferredSize(new Dimension(30, 40));//set độ dài độ rộng của header
        productTable.getTableHeader().setBackground(Color.RED);//set background cho header

        scrollPanel = new JScrollPane(productTable);
        scrollPanel.setBounds(10, 75, 710, 300);
        // tblQLS.getTableHeader().setBorder(BorderFactory.createLineBorder(null, 0, true));
        productTable.setPreferredSize(new Dimension(500, 500));
        productTable.setRowHeight(40);
        productTable.setGridColor(Color.GREEN);
        // scrollPanel.setPreferredSize(new Dimension(500,500));
//        scrollPanel.setViewportView(productTable);
        psanPham.add(scrollPanel); // add table vào scrollPanel
        productTable.addMouseListener(this);
        productTable.setFillsViewportHeight(true);//hiển thị table     
        productTable.setShowGrid(true);
        productTable.setDefaultEditor(Object.class, null);
        //---------------------- canh chinh do dai cot header
        productTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(70);
        //--------end Main table
        //user
        if (Login.type == 0) {
            //-----------------table gio hang
            JButton gioHangTitle = new JButton("Giỏ Hàng của Bạn");
            gioHangTitle.setFont(new Font("Arial", BOLD, 15));
            //ImageIcon tempIcon = new ImageIcon(getClass().getResource("./src/Image/refresh.png"));
            gioHangTitle.setIcon(new ImageIcon("./src/Image/cart.png"));
            gioHangTitle.setBounds(10, 395, 710, 30);
            psanPham.add(gioHangTitle);
            gioHangTable = CreateTable_GioHang();
            scrollPanel = new JScrollPane(gioHangTable);
            scrollPanel.setBounds(10, 420, 710, 220);
            psanPham.add(scrollPanel);
            
            //----------------end table gio hang
            //button
        }
        //admin
        if (Login.type == 1) {
            menuButton = new JButton[6];
            menuTextFieldADMIN = new JTextField[6];
            String[] arrMenu = {"Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Loại sản phẩm","Trạng thái", "Tên ảnh"};
            int toaDoXMenuButton = 20, toaDoYMenuButton = 400;
            for (int i = 0; i < arrMenu.length; i++) {
                menuButton[i] = new JButton(arrMenu[i]);
                menuButton[i].setBounds(toaDoXMenuButton, toaDoYMenuButton, 150, 30);
                menuButton[i].setFont(new Font("Arial", Font.PLAIN, 15));
                menuButton[i].setBackground(Color.GREEN);
                psanPham.add(menuButton[i]);
                
                menuTextFieldADMIN[i] = new JTextField();
                menuTextFieldADMIN[i].setBounds(toaDoXMenuButton + 160, toaDoYMenuButton, 170, 30);
                psanPham.add(menuTextFieldADMIN[i]);
                toaDoYMenuButton += 40;
            }
        }
        //------------end 
        //-----------------admin----------------
        JButton addADMIN = new JButton("Thêm");
        JButton deADMIN = new JButton("Xoá");
        JButton changeADMIN = new JButton("Sửa");
        JButton xuatexcel = new JButton("Xuất Excel");
        JButton chonAnhADMIN = new JButton("Chọn Ảnh");
        
        addADMIN.setBounds(400, 420, 100, 50);
        deADMIN.setBounds(400, 480, 100, 50);
        changeADMIN.setBounds(400, 540, 100, 50);
        xuatexcel.setBounds(550, 430, 100, 50);
        chonAnhADMIN.setBounds(550, 500, 100, 50);
        
        addADMIN.setBackground(Color.GREEN);
        deADMIN.setBackground(Color.GREEN);
        changeADMIN.setBackground(Color.GREEN);
        xuatexcel.setBackground(Color.GREEN);
        chonAnhADMIN.setBackground(Color.GREEN);
        
        addADMIN.setActionCommand("addADMIN");
        deADMIN.setActionCommand("xoaADMIN");
        changeADMIN.setActionCommand("suaADMIN");
        xuatexcel.setActionCommand("xuatEXCEL");
        chonAnhADMIN.setActionCommand("report");
        
        addADMIN.addActionListener(this);
        deADMIN.addActionListener(this);
        changeADMIN.addActionListener(this);
        chonAnhADMIN.addActionListener(this);
        xuatexcel.addActionListener(this);
        //------------------------------end
        JButton exe = new JButton("Lọc>>>");
        JButton xoaArrSP = new JButton("Xoá");
        JButton add = new JButton("Thêm");
        JButton print = new JButton("In hoá đơn ");
        JButton addCount = new JButton("+");
        JButton subtractCount = new JButton("-");
        refresh = new JButton(new ImageIcon("./src/Image/refresh.png"));
        find = new JButton(new ImageIcon("./src/Image/seach.png"));
        seachtxt = new JTextField();
        countTXT = new JTextField();
        

        //Jlable-- type = 0
        xoaArrSP.setBounds(323, 5, 80, 35);
        exe.setBounds(323, 40, 80, 35);
        refresh.setBounds(620, 20, 40, 40);
        seachtxt.setBounds(407, 20, 200, 40);
        find.setBounds(670, 20, 40, 40);
        add.setBounds(780, 330, 110, 50);
        print.setBounds(920, 330, 110, 50);
        addCount.setBounds(940, 280, 60, 30);
        subtractCount.setBounds(800, 280, 60, 30);
        countTXT.setBounds(880, 280, 40, 30);
        countTXT.setText(String.valueOf(count));
        countTXT.setEditable(false);
        countTXT.setHorizontalAlignment(countTXT.CENTER);
        
        xoaArrSP.setBackground(Color.red);
        exe.setBackground(Color.BLUE);
        add.setBackground(Color.red);
        add.setForeground(Color.white);
        print.setBackground(Color.BLUE);
        print.setForeground(Color.white);
        addCount.setBackground(Color.green);
        addCount.setForeground(Color.white);
        subtractCount.setBackground(Color.green);
        subtractCount.setForeground(Color.white);

        if (Login.type == 1) {
            psanPham.add(addADMIN);
            psanPham.add(deADMIN);
            psanPham.add(changeADMIN);
            psanPham.add(xuatexcel);
            psanPham.add(chonAnhADMIN);
        }
        psanPham.add(exe);
        psanPham.add(refresh);
        psanPham.add(seachtxt);
        psanPham.add(find);

        xoaArrSP.setActionCommand("xoaARRSP");
        exe.setActionCommand("exe");
        add.setActionCommand("add");
        print.setActionCommand("print");
        addCount.setActionCommand("+");
        subtractCount.setActionCommand("-");
        find.setActionCommand("find");
        refresh.setActionCommand("refresh");
        xoaArrSP.addActionListener(this);
        exe.addActionListener(this);
        add.addActionListener(this);
        print.addActionListener(this);
        addCount.addActionListener(this);
        subtractCount.addActionListener(this);
        find.addActionListener(this);
        refresh.addActionListener(this);
        
        this.setVisible(true);
        
        productTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent e){
                //Product Table
                if (Login.type == 0) {
                    psanPham.add(add);
                    psanPham.add(print);
                    psanPham.add(addCount);
                    psanPham.add(subtractCount);
                    psanPham.add(countTXT);
                    psanPham.add(xoaArrSP);
                }
                iconPanel.removeAll();
                JButton tongTienButton = new JButton("Tạm Tính");
                tongTienTXT = new JTextField();
                tongTienTXT.setText("0.0");
                tongTienTXT.setEditable(false);
                tongTienTXT.setBounds(730, 435, 340, 40);
                tongTienButton.setBounds(730, 400, 340, 30);
                tongTienButton.setFont(new Font("Arial", BOLD, 16));
                tongTienTXT.setFont(new Font("Arial", BOLD, 15));
                sanPhamPanel.add(tongTienButton);
                sanPhamPanel.add(tongTienTXT);
                int  i=productTable.getSelectedRow();
                if(i>=0)
                {   
                    iconPanel.setLayout(null);
                    iconPanel.setBackground(Color.PINK);
                    iconPanel.setBounds(730, 0, 350, 650);
                    Products = SanPhamBUS.Arr_products.get(i);
                    iconPizza = CreateJLable_Icon(0, -10, 350, 300, Products.img_path);
                    iconPanel.add(iconPizza);
                    sanPhamPanel.add(iconPanel);
                    sanPhamPanel.updateUI();
                }
                        //event
                if (Login.type == 1) {
                    try {
                        SanPhamBUS.docSanPham();
                    } catch (Exception ex) {
                        Logger.getLogger(HomeUser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int j = productTable.getSelectedRow();
                    if (j>=0){
                        menuTextFieldADMIN[0].setText((String)productTable.getModel().getValueAt(j, 0));
                        menuTextFieldADMIN[1].setText((String)productTable.getModel().getValueAt(j, 1));
                        menuTextFieldADMIN[2].setText((String)productTable.getModel().getValueAt(j, 2).toString());
                        menuTextFieldADMIN[3].setText((String)productTable.getModel().getValueAt(j, 3));
                        menuTextFieldADMIN[4].setText(String.valueOf(SanPhamBUS.Arr_products.get(j).getImg_path()));
                    }
                }

                //-----------------------------------------
            }
        });
        return psanPham;

    }

    public JPanel createJpanel_TxTAdmin() {
        JPanel pTXT = new JPanel();
        pTXT.setLayout(null);
        pTXT.setBounds(0, 420, 720, 300);
        pTXT.setBackground(Color.pink);
//        pTXT.add(lb);
        return pTXT;
    }

    public JTable CreateTable_GioHang() {
        gioHangTable = new JTable();
        headerGH = new Vector();
        headerGH.add("Mã sản phẩm");
        headerGH.add("Tên sản phẩm");
        headerGH.add("SL");
        headerGH.add("Đơn giá(VNĐ)");
        modelGioHang = new DefaultTableModel(headerGH, 0);
        gioHangTable = new JTable(null, headerGH);
        gioHangTable.setFont(new Font("Arial", 0, 15));
        gioHangTable.setModel(model);//add model len table
        gioHangTable.getTableHeader().setFont(new Font("Arial", BOLD, 16)); //set font cho vector header
        gioHangTable.getTableHeader().setForeground(Color.black); //set màu chữ cho header
        gioHangTable.getTableHeader().setPreferredSize(new Dimension(30, 40));//set độ dài độ rộng của header
        gioHangTable.getTableHeader().setBackground(Color.RED);//set background cho header

        // tblQLS.getTableHeader().setBorder(BorderFactory.createLineBorder(null, 0, true));
        //tGioHang.setPreferredSize(new Dimension(500,500));
        gioHangTable.setRowHeight(40);
        gioHangTable.setGridColor(Color.GREEN);
        // scrollPanel.setPreferredSize(new Dimension(500,500));
//        scrollPanel.setViewportView(productTable);
        gioHangTable.addMouseListener(this);
        gioHangTable.setFillsViewportHeight(true);//hiển thị table     
        gioHangTable.setShowGrid(false);
        gioHangTable.setDefaultEditor(Object.class, null);
        //---------------------- canh chinh do dai cot header
        gioHangTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        gioHangTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        gioHangTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        for (ProductsDTO product : Arr_GioHang) {
            Add_row_SanPham(product);
        }
        gioHangTable.setModel(modelGioHang);
        gioHangTable.updateUI();
        return gioHangTable;
    }
    
    public JPanel CreatePanel_QuanLiNhanVien(){
        pnhanvien = new JPanel();
        gioitinh_TextField = new JTextField();
        hoten_TextField = new JTextField();
        ngaysinh_TextField = new JTextField();
        idnhanvien_TextField = new JTextField();
        diachi_TextField = new JTextField();
        sdt_TextField = new JTextField();
        
        hotenLabel = new JLabel("Họ tên:");
        ngaysinhLabel = new JLabel("Ngày sinh:");
        idnhanvienLabel = new JLabel("ID nhân viên:");
        gioitinhLabel = new JLabel("Giới tính:");
        diachiLabel = new JLabel("Địa chỉ:");
        sdtLabel = new JLabel("SDT:");
        
        hotenLabel.setBounds(100, 450, 100, 40);
        ngaysinhLabel.setBounds(100, 500, 100, 40);
        idnhanvienLabel.setBounds(100, 400, 100, 40);
        gioitinhLabel.setBounds(100, 550, 100, 40);
        diachiLabel.setBounds(450, 400, 100, 40);
        sdtLabel.setBounds(450, 450, 100, 40);
        
        hoten_TextField.setBounds(200, 450, 200, 40);
        ngaysinh_TextField.setBounds(200, 500, 200, 40);
        idnhanvien_TextField.setBounds(200, 400, 200, 40);
        gioitinh_TextField.setBounds(200, 550, 200, 40);
        diachi_TextField.setBounds(520, 400, 200, 40);
        sdt_TextField.setBounds(520, 450, 200, 40);
        
        
        suataikhoan1Button = new JButton("Sửa");
        suataikhoan1Button.setActionCommand("suanhanvien");
        suataikhoan1Button.addActionListener(this);
        suataikhoan1Button.setBounds(750, 450, 200, 40);
        themtaikhoan1Button = new JButton("Thêm");
        themtaikhoan1Button.setActionCommand("themnhanvien");
        themtaikhoan1Button.addActionListener(this);
        themtaikhoan1Button.setBounds(750, 400, 200, 40);
        xoataikhoan1Button = new JButton("Xoá");
        xoataikhoan1Button.setActionCommand("xoanhanvien");
        xoataikhoan1Button.addActionListener(this);
        xoataikhoan1Button.setBounds(750, 500, 200, 40);
        QLNVExelButton = new JButton("Xuất Excel");
        QLNVExelButton.setActionCommand("QLNVExcel");
        QLNVExelButton.addActionListener(this);
        QLNVExelButton.setBackground(Color.green);
        QLNVExelButton.setBounds(750, 550, 200, 40);
        
        pnhanvien.add(suataikhoan1Button);
        pnhanvien.add(themtaikhoan1Button);
        pnhanvien.add(xoataikhoan1Button);
        pnhanvien.add(QLNVExelButton);
        pnhanvien.add(hoten_TextField);
        pnhanvien.add(ngaysinh_TextField);
        pnhanvien.add(gioitinh_TextField);
        pnhanvien.add(idnhanvien_TextField);
        pnhanvien.add(diachi_TextField);
        pnhanvien.add(sdt_TextField);
        pnhanvien.add(hotenLabel);
        pnhanvien.add(ngaysinhLabel);
        pnhanvien.add(idnhanvienLabel);
        pnhanvien.add(gioitinhLabel);
        pnhanvien.add(diachiLabel);
        pnhanvien.add(sdtLabel);
        
        pnhanvien.setLayout(null);
        pnhanvien.setBounds(0,0,1080,660);
        nhanvienTable = new JTable(null, headerNV);
        headerNV = new Vector();
        dataNV = new Vector();
        headerNV.add("ID");
        headerNV.add("Họ tên");
        headerNV.add("Ngày sinh");
        headerNV.add("Giới tính");
        headerNV.add("Địa chỉ");
        headerNV.add("SDT");
        modelNhanvien = new DefaultTableModel(headerNV, 0);
        nhanvienTable.setFont(new Font("Arial", 0, 15));
        nhanvienTable.setModel(modelNhanvien);//add model len table
        nhanvienTable.getTableHeader().setFont(new Font("Arial", BOLD, 18)); //set font cho vector header
        nhanvienTable.getTableHeader().setForeground(Color.black); //set màu chữ cho header
        nhanvienTable.getTableHeader().setPreferredSize(new Dimension(30, 40));//set độ dài độ rộng của header
        nhanvienTable.getTableHeader().setBackground(Color.RED);//set background cho header
        
        pnhanvien.add(nhanvienTable);
        
        scrollPanel1 = new JScrollPane(nhanvienTable);
        scrollPanel1.setBounds(50, 75, 950, 300);
        nhanvienTable.setPreferredSize(new Dimension(500,500));
        nhanvienTable.setRowHeight(40);
        nhanvienTable.setGridColor(Color.GREEN);

        pnhanvien.add(scrollPanel1);
        nhanvienTable.setFillsViewportHeight(true);
        nhanvienTable.setShowGrid(true);
        nhanvienTable.setDefaultEditor(Object.class, null); 

        nhanvienTable.getColumnModel().getColumn(0).setPreferredWidth(50); 
        nhanvienTable.getColumnModel().getColumn(1).setPreferredWidth(150); 
        nhanvienTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        nhanvienTable.getColumnModel().getColumn(3).setPreferredWidth(70);
        nhanvienTable.getColumnModel().getColumn(4).setPreferredWidth(200);
                try {
                    account_bus.docaccount();
                    for (AccountDTO acc : AccountBUS.Arr_account){
                        Vector tempp = new Vector();
                            tempp.add(acc.getId_nhanvien());
                            tempp.add(acc.getHoten());
                            tempp.add(acc.getNgaysinh());
                            tempp.add(acc.getGioitinh());
                            tempp.add(acc.getDiachi());
                            tempp.add(acc.getSodienthoai());
                            dataNV = tempp;
                            modelNhanvien.addRow(dataNV);
                        
                    }
                    nhanvienTable.setModel(modelNhanvien);
                } catch (Exception ae){
                }
        pnhanvien.setVisible(true);
        nhanvienTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent e){
                int j = nhanvienTable.getSelectedRow();
                if (j>=0){
                    idnhanvien_TextField.setText((String)nhanvienTable.getModel().getValueAt(j, 0));
                    hoten_TextField.setText((String)nhanvienTable.getModel().getValueAt(j, 1));
                    ngaysinh_TextField.setText((String)nhanvienTable.getModel().getValueAt(j, 2));
                    gioitinh_TextField.setText((String)nhanvienTable.getModel().getValueAt(j, 3));
                    diachi_TextField.setText((String)nhanvienTable.getModel().getValueAt(j, 4));
                    sdt_TextField.setText((String)nhanvienTable.getModel().getValueAt(j, 5));
                }
            }
        });
        return pnhanvien;
    }
    public JPanel CreatePanel_QuanLiTaiKhoan() {
        ptaikhoan = new JPanel();
        type_TextField = new JTextField();
        taikhoan_TextField = new JTextField();
        matkhau_TextField = new JTextField();
        id_TextField = new JTextField();
        
        taikhoanLabel = new JLabel("Tài khoản:");
        matkhauLabel = new JLabel("Mật khẩu:");
        idLabel = new JLabel("ID:");
        typeLabel = new JLabel("Phân quyền:");
        
        taikhoanLabel.setBounds(150, 450, 100, 40);
        matkhauLabel.setBounds(150, 500, 100, 40);
        idLabel.setBounds(150, 400, 100, 40);
        typeLabel.setBounds(150, 550, 100, 40);
        
        taikhoan_TextField.setBounds(250, 450, 200, 40);
        matkhau_TextField.setBounds(250, 500, 200, 40);
        id_TextField.setBounds(250, 400, 200, 40);
        type_TextField.setBounds(250, 550, 200, 40);
        
        suataikhoanButton = new JButton("Sửa");
        suataikhoanButton.setActionCommand("suataikhoan");
        suataikhoanButton.addActionListener(this);
        suataikhoanButton.setBounds(650, 450, 200, 40);
        themtaikhoanButton = new JButton("Thêm");
        themtaikhoanButton.setActionCommand("themtaikhoan");
        themtaikhoanButton.addActionListener(this);
        themtaikhoanButton.setBounds(650, 400, 200, 40);
        xoataikhoanButton = new JButton("Xoá");
        xoataikhoanButton.setActionCommand("xoataikhoan");
        xoataikhoanButton.addActionListener(this);
        xoataikhoanButton.setBounds(650, 500, 200, 40);
        QLTKExelButton = new JButton("Xuất Exel");
        QLTKExelButton.setActionCommand("QLTKExcel");
        QLTKExelButton.addActionListener(this);
        QLTKExelButton.setBackground(Color.green);
        QLTKExelButton.setBounds(650, 550, 200, 40);
        
        ptaikhoan.add(suataikhoanButton);
        ptaikhoan.add(themtaikhoanButton);
        ptaikhoan.add(QLTKExelButton);
        ptaikhoan.add(xoataikhoanButton);
        ptaikhoan.add(taikhoan_TextField);
        ptaikhoan.add(matkhau_TextField);
        ptaikhoan.add(type_TextField);
        ptaikhoan.add(id_TextField);
        ptaikhoan.add(taikhoanLabel);
        ptaikhoan.add(matkhauLabel);
        ptaikhoan.add(idLabel);
        ptaikhoan.add(typeLabel);
        
        ptaikhoan.setLayout(null);
        ptaikhoan.setBounds(0,0,1080,660);
        accountTabel = new JTable(null, headerTK);
        headerTK = new Vector();
        dataTK = new Vector();
        headerTK.add("ID");
        headerTK.add("Tài khoản");
        headerTK.add("Mật khẩu");
        headerTK.add("Phân quyền");
        modelTaiKhoan = new DefaultTableModel(headerTK, 0);
        accountTabel.setFont(new Font("Arial", 0, 15));
        accountTabel.setModel(modelTaiKhoan);//add model len table
        accountTabel.getTableHeader().setFont(new Font("Arial", BOLD, 18)); //set font cho vector header
        accountTabel.getTableHeader().setForeground(Color.black); //set màu chữ cho header
        accountTabel.getTableHeader().setPreferredSize(new Dimension(30, 40));//set độ dài độ rộng của header
        accountTabel.getTableHeader().setBackground(Color.RED);//set background cho header
        
        
        scrollPanel = new JScrollPane(accountTabel);
        scrollPanel.setBounds(150, 75, 710, 300);
        // tblQLS.getTableHeader().setBorder(BorderFactory.createLineBorder(null, 0, true));
        accountTabel.setPreferredSize(new Dimension(500,500));
        accountTabel.setRowHeight(40);
        accountTabel.setGridColor(Color.GREEN);
       // scrollPanel.setPreferredSize(new Dimension(500,500));
//        scrollPanel.setViewportView(productTable);
        ptaikhoan.add(scrollPanel); // add table vào scrollPanel
        accountTabel.setFillsViewportHeight(true);//hiển thị table     
        accountTabel.setShowGrid(true);
        accountTabel.setDefaultEditor(Object.class, null); 
        //---------------------- canh chinh do dai cot header
        accountTabel.getColumnModel().getColumn(0).setPreferredWidth(80); 
        accountTabel.getColumnModel().getColumn(1).setPreferredWidth(200); 
        accountTabel.getColumnModel().getColumn(2).setPreferredWidth(70);
       
                try {
                    login_bus.docdangnhap();
                    
                    for (LoginDTO login : LoginBUS.Arr_login){
                        Vector temp = new Vector();
                            temp.add(login.getId());
                            temp.add(login.getUsername());
                            temp.add(login.getPassword());
                            temp.add(login.getType());
                            dataTK = temp;
                            modelTaiKhoan.addRow(dataTK);
                        
                    }
                    accountTabel.setModel(modelTaiKhoan);
                } catch (Exception ae){
                }
                
                
        accountTabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent e){
                int j = accountTabel.getSelectedRow();
                if (j>=0){
                    id_TextField.setText((String)accountTabel.getModel().getValueAt(j, 0));
                    taikhoan_TextField.setText((String)accountTabel.getModel().getValueAt(j, 1));
                    matkhau_TextField.setText((String)accountTabel.getModel().getValueAt(j, 2));
                    type_TextField.setText((String)accountTabel.getModel().getValueAt(j, 3).toString());
                }
            }
        });        
        
        ptaikhoan.setVisible(true);
        return ptaikhoan;
    }
    
    public JPanel CreatePanel_ThongKeHD(){
        JPanel pthongke = new JPanel();
        pthongke.setLayout(null);
        pthongke.setBounds(0, 0, 1080, 660);
        
        //------------------------ panel tu Ngay Den Ngay
        JPanel pNgay = CreatePanel_NgayThang();
        pthongke.add(pNgay);
        //-------------------------
                
        JButton locButton = new JButton("Lọc>>");
        locButton.setBackground(Color.BLUE);
        locButton.setActionCommand("loc");
        locButton.addActionListener(this);
        locButton.setBounds(330, 25, 100, 40);
        pthongke.add(locButton);
        
        JButton xuatExcel = new JButton("Xuất Excel");
        xuatExcel.setBackground(Color.GREEN);
        xuatExcel.setActionCommand("xuatExcelThongKe");
        xuatExcel.addActionListener(this);
        xuatExcel.setBounds(440, 25, 100, 40);
        pthongke.add(xuatExcel);
        //--------------
        //------------ Table Tong tien theo QUy
        JTable thongKeHDTongTienTheoQuy = CreatePanel_ThongKeTongTienTheoQuy();
        
        scrollPanel = new JScrollPane(thongKeHDTongTienTheoQuy);
        scrollPanel.setBounds(10, 100, 1050, 100);
        thongKeHDTongTienTheoQuy.setRowHeight(40);
        thongKeHDTongTienTheoQuy.setGridColor(Color.GREEN);
//        pthongke.add(scrollPanel);
        
        //-----------------------------
        tongTienLabel= new JLabel();
        tongTienLabel.setFont(new Font("Arial", BOLD, 16));
        tongTienLabel.setBounds(10, 600, 1000, 40);
        pthongke.add(tongTienLabel);
        
        tongTienTheoNgayLabel = new JLabel();
        tongTienTheoNgayLabel.setFont(new Font("Arial", BOLD, 16));
        tongTienTheoNgayLabel.setBounds(450, 600, 500, 40);
        pthongke.add(tongTienTheoNgayLabel);
        //table HOADON
        JTable thongKeHDTable = CreatePanel_ThongKeHoaDon();
        scrollPanel = new JScrollPane(thongKeHDTable);
        scrollPanel.setBounds(10, 100, 1050, 500);
        // tblQLS.getTableHeader().setBorder(BorderFactory.createLineBorder(null, 0, true));
        thongKeHDTable.setRowHeight(40);
        thongKeHDTable.setGridColor(Color.GREEN);
        // scrollPanel.setPreferredSize(new Dimension(500,500));
//        scrollPanel.setViewportView(productTable);
        pthongke.add(scrollPanel);
        pthongke.updateUI();
        
        
        return pthongke;
    }
    
    public JPanel CreatePanel_NgayThang(){
        //---------------- panel Ngay
        JPanel pNgay = new JPanel();
        pNgay.setLayout(null);
        pNgay.setBounds(10, 10, 320, 60);
        
        ngayBatDauDate = new JDateChooser();
        ngayBatDauDate.setDateFormatString("yyyy-MM-dd");
        ngayBatDauDate.setDate(date);
        ngayBatDauDate.setBounds(10, 20, 150, 30);
        
        ngayKetThucDate = new JDateChooser();
        ngayKetThucDate.setDateFormatString("yyyy-MM-dd");
        ngayKetThucDate.setDate(date);
        ngayKetThucDate.setBounds(160, 20, 150, 30);
        
        Border menuTitleBoder = BorderFactory.createTitledBorder("Ngày:");
        pNgay.setBorder(menuTitleBoder);
                 
        pNgay.add(ngayBatDauDate);
        pNgay.add(ngayKetThucDate);
        return pNgay;
    }
    
    public JTable CreatePanel_ThongKeTongTienTheoQuy(){       
        thongKeHDTongTienTheoQuy = new JTable();
        headerGH = new Vector();
        for(int i = 1 ; i < 13 ; i++){
            headerGH.add("Tháng "+i);
        }
        headerGH.add("Tổng Cộng");
        
        modelthongKeHDTongTienTheoQuy = new DefaultTableModel(headerGH, 0);
        thongKeHDTongTienTheoQuy = new JTable(null, headerGH);
        thongKeHDTongTienTheoQuy.setFont(new Font("Arial", 0, 13));
        thongKeHDTongTienTheoQuy.setModel(modelthongKeHDTongTienTheoQuy);//add model len table
        thongKeHDTongTienTheoQuy.getTableHeader().setFont(new Font("Arial", BOLD, 14)); //set font cho vector header
        thongKeHDTongTienTheoQuy.getTableHeader().setForeground(Color.black); //set màu chữ cho header
        thongKeHDTongTienTheoQuy.getTableHeader().setPreferredSize(new Dimension(30, 40));//set độ dài độ rộng của header
        thongKeHDTongTienTheoQuy.getTableHeader().setBackground(Color.RED);//set background cho header

        // tblQLS.getTableHeader().setBorder(BorderFactory.createLineBorder(null, 0, true));
        //tGioHang.setPreferredSize(new Dimension(500,500));
        thongKeHDTongTienTheoQuy.setRowHeight(40);
        thongKeHDTongTienTheoQuy.setGridColor(Color.GREEN);
        // scrollPanel.setPreferredSize(new Dimension(500,500));
//        scrollPanel.setViewportView(productTable);
        thongKeHDTongTienTheoQuy.addMouseListener(this);
        thongKeHDTongTienTheoQuy.setFillsViewportHeight(true);//hiển thị table     
        thongKeHDTongTienTheoQuy.setShowGrid(true);
        thongKeHDTongTienTheoQuy.setDefaultEditor(Object.class, null);
        
        thongKeHDTongTienTheoQuy.getColumnModel().getColumn(12).setPreferredWidth(100);
        
        //---------------------- canh chinh do dai cot header
        for(HoaDonDTO hoadons : HoaDonBUS.Arr_HoaDonBUS){
            Vector ngayBDVecTor = HoaDonBUS.splitDAY(df.format(ngayBatDauDate.getDate()));
        }

//        int namBD= Integer.valueOf(ngayBDVecTor.get(0).toString());
        int namBDa = 2020;
        for (HoaDonDTO hoadons : HoaDonBUS.Arr_HoaDonBUS) {
            Add_row_ThongKeHDTongTienTheoQuy(2000);
        }
        thongKeHDTongTienTheoQuy.setModel(modelthongKeHDTongTienTheoQuy);
        thongKeHDTongTienTheoQuy.updateUI();
        return thongKeHDTongTienTheoQuy;
    }
    
    public JTable CreatePanel_ThongKeHoaDon(){
        thongKeHDTable = new JTable();
        headerGH = new Vector();
        headerGH.add("Mã hoá đơn");
        headerGH.add("Mã nhân viên");
        headerGH.add("Mã khách hàng");
        headerGH.add("Ngày lập");
        headerGH.add("Thành tiền(VNĐ)");
        
        modelThongKeHD = new DefaultTableModel(headerGH, 0);
        thongKeHDTable = new JTable(null, headerGH);
        thongKeHDTable.setFont(new Font("Arial", 0, 15));
        thongKeHDTable.setModel(modelThongKeHD);//add model len table
        thongKeHDTable.getTableHeader().setFont(new Font("Arial", BOLD, 16)); //set font cho vector header
        thongKeHDTable.getTableHeader().setForeground(Color.black); //set màu chữ cho header
        thongKeHDTable.getTableHeader().setPreferredSize(new Dimension(30, 40));//set độ dài độ rộng của header
        thongKeHDTable.getTableHeader().setBackground(Color.RED);//set background cho header

        // tblQLS.getTableHeader().setBorder(BorderFactory.createLineBorder(null, 0, true));
        //tGioHang.setPreferredSize(new Dimension(500,500));
        thongKeHDTable.setRowHeight(30);
        thongKeHDTable.setGridColor(Color.GREEN);
               
        thongKeHDTable.addMouseListener(this);
        thongKeHDTable.setFillsViewportHeight(true);//hiển thị table     
        thongKeHDTable.setShowGrid(false);
        thongKeHDTable.setDefaultEditor(Object.class, null);
        //---------------------- canh chinh do dai cot header
        thongKeHDTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        thongKeHDTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        thongKeHDTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        thongKeHDTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        thongKeHDTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        try {
            HoaDonBUS.docHoaDon();
        } catch (Exception ex) {
            Logger.getLogger(HomeUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (HoaDonDTO hoadon : HoaDonBUS.Arr_HoaDonBUS) {
            Add_row_ThongKeHD(hoadon);
        }
        thongKeHDTable.setModel(modelThongKeHD);
        thongKeHDTable.updateUI();
        return thongKeHDTable;
    }
    public JPanel CreatePanel_QuanLiHoaDon(){
        JPanel phoadon = new JPanel();
        phoadon.setLayout(null);
        phoadon.setBounds(0, 0, 1080, 660);
        
        refresh = new JButton(new ImageIcon("./src/Image/refresh.png"));
        find = new JButton(new ImageIcon("./src/Image/seach.png"));
        seachtxt = new JTextField();
        //
        refresh.setBounds(770, 20, 40, 40);
        seachtxt.setBounds(300, 20, 400, 40);
        find.setBounds(720, 20, 40, 40);
        phoadon.add(refresh);
        phoadon.add(seachtxt);
        phoadon.add(find);
        find.addActionListener(this);
        refresh.addActionListener(this);
        find.setActionCommand("findHD");
        refresh.setActionCommand("refreshHD");
        
        JButton anHoaDon = new JButton("Ẩn Hoá Đơn");
        anHoaDon.setActionCommand("anhoadon");
        anHoaDon.addActionListener(this);
        anHoaDon.setBounds(10, 540, 200, 40);
        
        JButton xemChiTietHD = new JButton("Chi Tiết");
        xemChiTietHD.setActionCommand("chitiethoadon");
        xemChiTietHD.addActionListener(this);
        xemChiTietHD.setBounds(470, 540, 100, 40);
        
        JButton xoaHoaDon = new JButton("Format...");
        xoaHoaDon.setActionCommand("format");
        xoaHoaDon.addActionListener(this);
        xoaHoaDon.setBounds(240, 540, 200, 40);
        
        JButton QLHDExelButton = new JButton("Xuất Excel");
        QLHDExelButton.setActionCommand("hoadonexcel");
        QLHDExelButton.addActionListener(this);
        QLHDExelButton.setBackground(Color.green);
        QLHDExelButton.setBounds(630, 540, 200, 40);
        
        JButton QLHDReportButton = new JButton("Xuất Report");
        QLHDReportButton.setActionCommand("hoadonreport");
        QLHDReportButton.addActionListener(this);
        QLHDReportButton.setBackground(Color.green);
        QLHDReportButton.setBounds(860, 540, 200, 40);
        
        phoadon.add(anHoaDon);
        phoadon.add(xoaHoaDon);
        phoadon.add(QLHDExelButton);
        phoadon.add(QLHDReportButton);
        phoadon.add(xemChiTietHD);
        
        hoaDonTable = new JTable();
        headerGH = new Vector();
        headerGH.add("Mã hoá đơn");
        headerGH.add("Mã nhân viên");
        headerGH.add("Mã khách hàng");
        headerGH.add("Mã giảm giá");
        headerGH.add("Ngày lập");
        headerGH.add("Thành tiền(VNĐ)");
        headerGH.add("Trạng thái");
        
        modelHoadon = new DefaultTableModel(headerGH, 0);
        hoaDonTable = new JTable(null, headerGH);
        hoaDonTable.setFont(new Font("Arial", 0, 15));
        hoaDonTable.setModel(modelHoadon);//add model len table
        hoaDonTable.getTableHeader().setFont(new Font("Arial", BOLD, 16)); //set font cho vector header
        hoaDonTable.getTableHeader().setForeground(Color.black); //set màu chữ cho header
        hoaDonTable.getTableHeader().setPreferredSize(new Dimension(30, 40));//set độ dài độ rộng của header
        hoaDonTable.getTableHeader().setBackground(Color.RED);//set background cho header

        // tblQLS.getTableHeader().setBorder(BorderFactory.createLineBorder(null, 0, true));
        //gioHangTable.setPreferredSize(new Dimension(500,500));
        hoaDonTable.setRowHeight(30);
        hoaDonTable.setGridColor(Color.GREEN);
        
        scrollPanel = new JScrollPane(hoaDonTable);
        scrollPanel.setBounds(10, 75, 1050, 440);
        // tblQLS.getTableHeader().setBorder(BorderFactory.createLineBorder(null, 0, true));
        hoaDonTable.setPreferredSize(new Dimension(500, 500));
        hoaDonTable.setRowHeight(40);
        hoaDonTable.setGridColor(Color.GREEN);
        // scrollPanel.setPreferredSize(new Dimension(500,500));
//        scrollPanel.setViewportView(productTable);
        phoadon.add(scrollPanel);
        
        hoaDonTable.addMouseListener(this);
        hoaDonTable.setFillsViewportHeight(true);//hiển thị table     
        hoaDonTable.setShowGrid(false);
        hoaDonTable.setDefaultEditor(Object.class, null);
        //---------------------- canh chinh do dai cot header
        hoaDonTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        hoaDonTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        hoaDonTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        hoaDonTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        hoaDonTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        hoaDonTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        try {
            HoaDonBUS.docHoaDon();
        } catch (Exception ex) {
            Logger.getLogger(HomeUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (HoaDonDTO hoadon : HoaDonBUS.Arr_HoaDonBUS) {
            Add_row_Hoadon(hoadon);
        }
        hoaDonTable.setModel(modelHoadon);
        hoaDonTable.updateUI();
        
        phoadon.setVisible(true);
        return phoadon;
    }
    
    public JPanel CreatePanel_QuanLiKhachhang(){
        pkhachhang = new JPanel();
        pkhachhang.setBackground(Color.PINK);
        hotenkh_TextField = new JTextField();
        sdtkh_TextField = new JTextField();
        gmail_TextField = new JTextField();
        idkh_TextField = new JTextField();
        
        seachtxt = new JTextField();
        JButton refresh = new JButton(new ImageIcon("./src/Image/refresh.png"));
        find = new JButton(new ImageIcon("./src/Image/seach.png"));

        refresh.setBounds(770, 20, 40, 40);
        seachtxt.setBounds(300, 20, 400, 40);
        find.setBounds(720, 20, 40, 40);
        
        find.setActionCommand("findkh");
        refresh.setActionCommand("refreshkh");
        find.addActionListener(this);
        refresh.addActionListener(this);
        
        pkhachhang.add(find);
        pkhachhang.add(seachtxt);
        pkhachhang.add(refresh);
        
        taikhoanLabel = new JLabel("Tên khách hàng:");
        matkhauLabel = new JLabel("SDT:");
        idLabel = new JLabel("ID Khách hàng:");
        typeLabel = new JLabel("Gmail:");
        
        taikhoanLabel.setBounds(150, 450, 100, 40);
        matkhauLabel.setBounds(150, 500, 100, 40);
        idLabel.setBounds(150, 400, 100, 40);
        typeLabel.setBounds(150, 550, 100, 40);
        
        hotenkh_TextField.setBounds(250, 450, 200, 40);
        sdtkh_TextField.setBounds(250, 500, 200, 40);
        idkh_TextField.setBounds(250, 400, 200, 40);
        gmail_TextField.setBounds(250, 550, 200, 40);
        
        suataikhoanButton = new JButton("Sửa");
        suataikhoanButton.setActionCommand("suakhachhang");
        suataikhoanButton.addActionListener(this);
        suataikhoanButton.setBounds(650, 450, 200, 40);
        themtaikhoanButton = new JButton("Thêm");
        themtaikhoanButton.setActionCommand("themkhachhang");
        themtaikhoanButton.addActionListener(this);
        themtaikhoanButton.setBounds(650, 400, 200, 40);
        xoataikhoanButton = new JButton("Xoá");
        xoataikhoanButton.setActionCommand("xoakhachhang");
        xoataikhoanButton.addActionListener(this);
        xoataikhoanButton.setBounds(650, 500, 200, 40);
        JButton QLKHExelButton = new JButton("Xuất Exel");
        QLKHExelButton.setActionCommand("QLKHExcel");
        QLKHExelButton.addActionListener(this);
        QLKHExelButton.setBackground(Color.green);
        QLKHExelButton.setBounds(650, 550, 200, 40);
        
        pkhachhang.add(suataikhoanButton);
        pkhachhang.add(themtaikhoanButton);
        pkhachhang.add(QLKHExelButton);
        pkhachhang.add(xoataikhoanButton);
        pkhachhang.add(hotenkh_TextField);
        pkhachhang.add(sdtkh_TextField);
        pkhachhang.add(gmail_TextField);
        pkhachhang.add(idkh_TextField);
        pkhachhang.add(taikhoanLabel);
        pkhachhang.add(matkhauLabel);
        pkhachhang.add(idLabel);
        pkhachhang.add(typeLabel);

        
        pkhachhang.setLayout(null);
        pkhachhang.setBounds(0,0,1080,660);
        khachhangtable = new JTable(null, headerKH);
        headerKH = new Vector();
        dataKH = new Vector();
        headerKH.add("ID KH");
        headerKH.add("Tên khách hàng");
        headerKH.add("SĐT");
        headerKH.add("Gmail");
        modelKhachhang = new DefaultTableModel(headerKH, 0);
        khachhangtable.setFont(new Font("Arial", 0, 15));
        khachhangtable.setModel(modelKhachhang);//add model len table
        khachhangtable.getTableHeader().setFont(new Font("Arial", BOLD, 18)); //set font cho vector header
        khachhangtable.getTableHeader().setForeground(Color.black); //set màu chữ cho header
        khachhangtable.getTableHeader().setPreferredSize(new Dimension(30, 40));//set độ dài độ rộng của header
        khachhangtable.getTableHeader().setBackground(Color.RED);//set background cho header
        
        
        scrollPanel = new JScrollPane(khachhangtable);
        scrollPanel.setBounds(150, 75, 760, 300);
        khachhangtable.setPreferredSize(new Dimension(500,500));
        khachhangtable.setRowHeight(40);
        khachhangtable.setGridColor(Color.GREEN);
        pkhachhang.add(scrollPanel); // add table vào scrollPanel
        khachhangtable.setFillsViewportHeight(true);//hiển thị table     
        khachhangtable.setShowGrid(true);
        khachhangtable.setDefaultEditor(Object.class, null); 
        //---------------------- canh chinh do dai cot header
        khachhangtable.getColumnModel().getColumn(0).setPreferredWidth(30); 
        khachhangtable.getColumnModel().getColumn(1).setPreferredWidth(100); 
        khachhangtable.getColumnModel().getColumn(2).setPreferredWidth(30);
       
                try {
                    khachhang_bus.docKhachHang();
                    
                    for (KhachHangDTO khachhang : KhachHangBUS.Arr_khachhang){
                        Vector temp = new Vector();
                            temp.add(khachhang.getID_Khachhang());
                            temp.add(khachhang.getTenKhachHang());
                            temp.add(khachhang.getSDT());
                            temp.add(khachhang.getGmail());
                            dataKH = temp;
                            modelKhachhang.addRow(dataKH);
                        
                    }
                    khachhangtable.setModel(modelKhachhang);
                } catch (Exception ae){
                }
        
        pkhachhang.setVisible(true);
        khachhangtable.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent e){
                int j = khachhangtable.getSelectedRow();
                if (j>=0){
                    idkh_TextField.setText((String)khachhangtable.getModel().getValueAt(j, 0));
                    hotenkh_TextField.setText((String)khachhangtable.getModel().getValueAt(j, 1));
                    sdtkh_TextField.setText((String)khachhangtable.getModel().getValueAt(j, 2));
                    gmail_TextField.setText((String)khachhangtable.getModel().getValueAt(j, 3).toString());
                }
            }
        });
        return pkhachhang;
    }
    
    public JPanel CreatePanel_TimKiemTheoGia(){
        JPanel ptimkiem = new JPanel();
        ptimkiem.setLayout(null);
//        ptimkiem.setBackground(Color.white);
        ptimkiem.setBounds(10, 10, 150, 60);
        
        Border menuTitleBoder = BorderFactory.createTitledBorder("Khoảng Giá");
        ptimkiem.setBorder(menuTitleBoder);
        
        giaTriDauTXT = new JTextField();
        giaTriCuoiTXT = new JTextField();
        
        giaTriDauTXT.setBounds(10, 20, 60, 25);
        giaTriCuoiTXT.setBounds(80, 20, 60, 25);     
        //ptimkiem.add(khoangGia);
        ptimkiem.add(giaTriCuoiTXT);
        ptimkiem.add(giaTriDauTXT);
        return ptimkiem;
    }
    
    public JPanel CreatePanel_TimKiemLoai(){
        JPanel ptimkiem = new JPanel();
  //      Vector VTCombobox = SanPhamBUS.timkiemTheoLoai();
        ptimkiem.setLayout(null);
//      ptimkiem.setBackground(Color.white);
        ptimkiem.setBounds(170, 10, 150, 60);
        
        Border menuTitleBoder = BorderFactory.createTitledBorder("Theo Loại Sản Phẩm");
        ptimkiem.setBorder(menuTitleBoder);
   
        loaiSanPhamCBB = new JComboBox(SanPhamBUS.CreateComboBOX());
        loaiSanPhamCBB.setBounds(5, 20, 140, 30);
        ptimkiem.add(loaiSanPhamCBB);
        loaiSanPhamCBB.setActionCommand("combobox");
        loaiSanPhamCBB.addActionListener(this);
        
        return ptimkiem;
    }
    
    private void Add_header(String a,String b,String c,String d) {
        Vector header = new Vector();
        header.add(a);
        header.add(b);
        header.add(c);
        header.add(d);
        if (model.getRowCount() == 0) {
            model = new DefaultTableModel(header, 0);
        }
    }
    
    private void Add_header(String a,String b,String c,String d,String e) {
        Vector header = new Vector();
        header.add(a);
        header.add(b);
        header.add(c);
        header.add(d);
        header.add(e);
        if (modelGioHang.getRowCount() == 0) {
            modelGioHang = new DefaultTableModel(header, 0);
        }
    }

    private void Add_row_SanPham(ProductsDTO products) {
        Vector row = new Vector();
        row.add(products.getID_Product());
        row.add(products.getName());
        row.add(products.getPice());
        row.add(products.getCategory());     
        model.addRow(row);
//      productTable.setModel(model);
    }  
    
    private void Add_row_Hoadon(HoaDonDTO hoadon) {
        Vector row = new Vector();
        row.add(hoadon.getID_Hoadon());
        row.add(hoadon.getID_NhanVien());
        row.add(hoadon.getID_Khachhang());
        row.add(hoadon.getID_Giamgia());     
        row.add(hoadon.getNgaylap());
        row.add(hoadon.getThanhTien());
        row.add(hoadon.getTrangThai());
        modelHoadon.addRow(row);
    }  
    
    private void Add_row_ThongKeHD(HoaDonDTO hoadon) {
        Vector row = new Vector();
        row.add(hoadon.getID_Hoadon());
        row.add(hoadon.getID_NhanVien());
        row.add(hoadon.getID_Khachhang());  
        row.add(hoadon.getNgaylap());
        row.add(hoadon.getThanhTien());
        modelThongKeHD.addRow(row);
    }  
    
    private void Add_row_ThongKeHDTongTienTheoQuy(int tongTien) {
        Vector row = new Vector();
        for(int i = 1 ; i < 13 ; i++){
            row.add(tongTien);
        }
        modelThongKeHD.addRow(row);
    }  
   
    
    public static void Add_row_GioHang(ProductsDTO products) {
        Vector row = new Vector();
        row.add(products.getID_Product());
        row.add(products.getName());
        row.add(products.getAmount());
        row.add(products.getPice());  
        modelGioHang.addRow(row);
//      productTable.setModel(model);
    }
    
    public JPanel CreateJPanel_TaiKhoan (){
        JPanel taiKhoanPanel = new JPanel();
        JLabel iconAccount = CreateJLable_Icon(130, 120, 256, 256, "/Image/doctor.png");
        AccountBUS bus = new AccountBUS();
        taiKhoanPanel.setLayout(null);
        taiKhoanPanel.setBackground(Color.pink);
        taiKhoanPanel.setBounds(0, 0, 1080, 660);
        
        menuLabel = new JLabel[5];
        menuTextField = new JTextField[5];
        String[] arrtaikhoan = {"Họ và tên:","Ngày sinh:", "Giới tính:", "Số điện thoại:","Địa chỉ"} ;
        JLabel header_taikhoan = new JLabel("Thông tin cá nhân");
        header_taikhoan.setBounds(400, 30, 300, 50);
        header_taikhoan.setFont(new Font("Arial",Font.PLAIN,28));
        taiKhoanPanel.add(header_taikhoan);
        int toaDoXMenuButton=500,toaDoYMenuButton=100;
        for(int i=0;i<arrtaikhoan.length; i++)
        {
            menuLabel[i] = new JLabel(arrtaikhoan[i]);
            menuLabel[i].setBounds(toaDoXMenuButton, toaDoYMenuButton, 190, 50);
            menuLabel[i].setFont(new Font("Arial", Font.PLAIN, 20));
            menuLabel[i].setBackground(Color.GREEN);
            taiKhoanPanel.add(menuLabel[i]);
            toaDoYMenuButton += 70;
        }
        int toaDoXMenuText=650,toaDoYMenuText=100;
        for(int i=0;i<arrtaikhoan.length; i++){
            menuTextField[i] = new JTextField();
            menuTextField[i].setBounds(toaDoXMenuText, toaDoYMenuText, 190, 40);
            menuTextField[i].setFont(new Font("Arial", Font.PLAIN, 20));
//            menuTextField[i].setEditable(false);          
            taiKhoanPanel.add(menuTextField[i]);
            toaDoYMenuText += 70;      
        }
        
            if (AccountBUS.Arr_account.size() == 0){
                try {
                    bus.docaccount();
                    for (AccountDTO account : AccountBUS.Arr_account){
                        if (Login.idhienhanh.equals(account.getId_nhanvien())){
                            menuTextField[0].setText(account.hoten);
                            menuTextField[1].setText(account.ngaysinh);
                            menuTextField[2].setText(account.gioitinh);
                            menuTextField[3].setText(account.sodienthoai);
                            menuTextField[4].setText(account.diachi);
                            break;
                        }
                        
                    }
                } catch (Exception ae){
                }
            }
            else{
                try {
                    for (AccountDTO account : AccountBUS.Arr_account){
                        if (Login.idhienhanh.equals(account.getId_nhanvien())){
                        menuTextField[0].setText(account.hoten);
                        menuTextField[1].setText(account.ngaysinh);
                        menuTextField[2].setText(account.gioitinh);
                        menuTextField[3].setText(account.sodienthoai);
                        menuTextField[4].setText(account.diachi);
                        break;
                        }
                        
                    }
                }catch(Exception ex){
                }
            }
        JButton confirm_taikhoan = new JButton("Cập nhật");
        confirm_taikhoan.setBounds(600, 450, 200, 40);
        confirm_taikhoan.setFont(new Font("Arial", Font.PLAIN, 16));
        confirm_taikhoan.setBackground(Color.GREEN);
        confirm_taikhoan.setActionCommand("confirm");
        confirm_taikhoan.addActionListener(this);
        JButton change_taikhoan = new JButton("Đổi mật khẩu");
        change_taikhoan.setBounds(300, 450, 150, 40);
        change_taikhoan.setFont(new Font("Arial", Font.PLAIN, 16));
        change_taikhoan.setBackground(Color.GREEN);
        change_taikhoan.setActionCommand("changepass");
        change_taikhoan.addActionListener(this);
        
        taiKhoanPanel.add(confirm_taikhoan);
        taiKhoanPanel.add(change_taikhoan);
        taiKhoanPanel.add(iconAccount);
        taiKhoanPanel.updateUI();
        return taiKhoanPanel;
    }
    
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override   
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
        case KeyEvent.VK_ENTER :{
            if(e.getSource() == find)
               System.out.println("A and Ctrl are pressed.");
            else
                System.out.println("Only A is pressed");
            break;
        }
        case KeyEvent.VK_F5 :{
            seachtxt.setText("");
                model.setRowCount(0);
                for (int i =0; i < SanPhamBUS.Arr_products.size();i++) {
                    Add_row_SanPham(SanPhamBUS.Arr_products.get(i));
                }
            break;
        }
    }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
            
    }
            
    @Override
    public void mousePressed(MouseEvent e){
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
           //-------NhanVIen
            //panel trang chu
            if("buttonStaff_0".equals(e.getActionCommand()))
            {    
                contentPanel.removeAll();
                JPanel trangChuPanel = CreateJPanel_TrangChu();
                contentPanel.add(trangChuPanel);
                contentPanel.setLayout(null);
                contentPanel.updateUI();
            }
            //panel san pham
            else if("buttonStaff_1".equals(e.getActionCommand()))
            {   
                contentPanel.removeAll();
                sanPhamPanel =CreateJPanel_SanphamTable(); 
                contentPanel.add(sanPhamPanel);
                contentPanel.setLayout(null);
                contentPanel.updateUI();     
                //trangChuPanel.setVisible(false);  
               
            }
            //panel tai khoan
           else if("buttonStaff_2".equals(e.getActionCommand()))
            {   
                contentPanel.removeAll();
                JPanel taiKhoanPanel =CreateJPanel_TaiKhoan(); 
                contentPanel.add(taiKhoanPanel);
                contentPanel.setLayout(null);
                contentPanel.updateUI();
            }
            else if("buttonStaff_3".equals(e.getActionCommand()))
            {
                dispose();
                new Login();
            }
            else if("buttonStaff_4".equals(e.getActionCommand()))
            {   
                    JOptionPane.showMessageDialog(this, "Cảm ơn đã sử dụng Phần Mềm");
                    System.exit(0);
               
            }
            
            //-----------admin
            if("buttonAdmin_0".equals(e.getActionCommand()))
            {    
                contentPanel.removeAll();
                sanPhamPanel =CreateJPanel_SanphamTable(); 
                contentPanel.add(sanPhamPanel);
                contentPanel.setLayout(null);
                contentPanel.updateUI(); 
                
            }
            if("buttonAdmin_1".equals(e.getActionCommand()))
            {    
  
                contentPanel.removeAll();
                JPanel QLNV_Panel =CreatePanel_QuanLiNhanVien();
                contentPanel.add(QLNV_Panel);
                contentPanel.setLayout(null);
                contentPanel.updateUI();  
            }
            if("buttonAdmin_2".equals(e.getActionCommand()))
            {    
                contentPanel.removeAll();
                JPanel QLTK_Panel =CreatePanel_QuanLiTaiKhoan();
                contentPanel.add(QLTK_Panel);
                contentPanel.setLayout(null);
                contentPanel.updateUI();      
            }
            if("buttonAdmin_3".equals(e.getActionCommand()))
            {    
                contentPanel.removeAll();
                JPanel sanPhamPanel =CreatePanel_QuanLiKhachhang();
                contentPanel.add(sanPhamPanel);
                contentPanel.setLayout(null);
                contentPanel.updateUI();     
            }
            if("buttonAdmin_4".equals(e.getActionCommand()))
            {    
                contentPanel.removeAll();
                JPanel hoaDonPanel = CreatePanel_QuanLiHoaDon();
                contentPanel.add(hoaDonPanel);
                contentPanel.setLayout(null);
                contentPanel.updateUI();      
            }
            if("buttonAdmin_5".equals(e.getActionCommand()))
            {    
                contentPanel.removeAll();
                JPanel thongKePanel =CreatePanel_ThongKeHD();
                contentPanel.add(thongKePanel);
                contentPanel.setLayout(null);
                contentPanel.updateUI();       
            }
            if("buttonAdmin_6".equals(e.getActionCommand()))
            {    
                dispose();
                new Login();
            }
            if("buttonAdmin_7".equals(e.getActionCommand()))
            {    
                System.exit(0);  
            }
            
            //-----------------------------san pham Type = 0
            if("add".equals(e.getActionCommand()))
            {
                ArrayList<ProductsDTO> tempArr = new ArrayList<>();
                int  i=productTable.getSelectedRow();
                if(i>=0)
                {   
                    ProductsDTO product = new ProductsDTO(); 
                    product = SanPhamBUS.Arr_products.get(i);
                    product.setAmount(Integer.valueOf(countTXT.getText()));
                    if (!Arr_GioHang.contains(product)){
                        Arr_GioHang.add(product);
                        Add_row_GioHang(Arr_GioHang.get(check));                     
                        Tong = Tong + Arr_GioHang.get(check).getAmount()*Arr_GioHang.get(check).getPice();
                        check++; 
                    }else{
                        if (JOptionPane.showConfirmDialog(null, "Sản Phẩm đã tồn tại, Bạn có muốn Thay Đổi", "Thông báo",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            JOptionPane.showMessageDialog(this, "Thành công");
                        }
                    }
                } 
                tongTienTXT.setText(String.valueOf(Tong));
                tongTienTXT.setEditable(false);
                count = 1;
                countTXT.setText(String.valueOf(count));
                System.out.println(Tong);
            }            
            if("find".equals(e.getActionCommand()))
            {
                String temp = seachtxt.getText();
                model.setRowCount(0);
                for (int i =0; i < SanPhamBUS.timkiemALL(temp).size();i++) {
                    Add_row_SanPham(SanPhamBUS.timkiemALL(temp).get(i));
                }
            }
            
            if("findnv".equals(e.getActionCommand()))
            {
                String temp = seachtxt.getText();
                NhanVienBUS nhanvien_bus = new NhanVienBUS();
                System.out.println(temp);
                modelNhanvien.setRowCount(0);
               try {
                   nhanvien_bus.docaccount();
                   for (NhanVienDTO acc : NhanVienBUS.findNhanvien(temp)){
                        Vector tempp = new Vector();
                            tempp.add(acc.getID_NhanVien());
                            tempp.add(acc.getTenNhanVien());
                            tempp.add(acc.getNgaySinh());
                            tempp.add(acc.getGioiTinh());
                            tempp.add(acc.getDiaChi());
                            tempp.add(acc.getSDT());
                            dataNV = tempp;
                            modelNhanvien.addRow(dataNV);                        
                    }
                nhanvienTable.setModel(modelNhanvien);
               } catch (Exception ex) {
                   Logger.getLogger(HomeUser.class.getName()).log(Level.SEVERE, null, ex);
               }   
            }
            if("findtk".equals(e.getActionCommand()))
            {
                String temp = seachtxt.getText();
                LoginBUS taikhoan_bus = new LoginBUS();
                modelTaiKhoan.setRowCount(0);
               try {
                   taikhoan_bus.docdangnhap();
                   for (LoginDTO login : LoginBUS.findTaikhoan(temp)){
                        Vector tempp = new Vector();
                            tempp.add(login.getId());
                            tempp.add(login.getUsername());
                            tempp.add(login.getPassword());
                            tempp.add(login.getType());
                            dataTK = tempp;
                            modelTaiKhoan.addRow(dataTK);
                        
                    }
                accountTabel.setModel(modelTaiKhoan);
               } catch (Exception ex) {
                   Logger.getLogger(HomeUser.class.getName()).log(Level.SEVERE, null, ex);
               }   
            }
            if("findkh".equals(e.getActionCommand()))
            {
                String temp = seachtxt.getText();
                KhachHangBUS khachhang_bus = new KhachHangBUS();
                modelKhachhang.setRowCount(0);
               try {
                   khachhang_bus.docKhachHang();
                   for (KhachHangDTO kh : KhachHangBUS.findKhachhang(temp)){
                        Vector tempp = new Vector();
                            tempp.add(kh.getID_Khachhang());
                            tempp.add(kh.getTenKhachHang());
                            tempp.add(kh.getSDT());
                            tempp.add(kh.getGmail());
                            dataKH = tempp;
                            modelKhachhang.addRow(dataKH);
                        
                    }
                khachhangtable.setModel(modelKhachhang);
               } catch (Exception ex) {
                   Logger.getLogger(HomeUser.class.getName()).log(Level.SEVERE, null, ex);
               }   
            }
            
            if("refresh".equals(e.getActionCommand()))
            {
                seachtxt.setText("");
                model.setRowCount(0);
                modelGioHang.setRowCount(0);
                for (int i =0; i < SanPhamBUS.Arr_products.size();i++) {
                    Add_row_SanPham(SanPhamBUS.Arr_products.get(i));
                }       
               
            }
            if("refreshkh".equals(e.getActionCommand()))
            {
                seachtxt.setText("");
                model.setRowCount(0);
                for (KhachHangDTO khachhang : KhachHangBUS.Arr_khachhang){
                        Vector temp = new Vector();
                            temp.add(khachhang.getID_Khachhang());
                            temp.add(khachhang.getTenKhachHang());
                            temp.add(khachhang.getSDT());
                            temp.add(khachhang.getGmail());
                            dataKH = temp;
                            modelKhachhang.addRow(dataKH);
                        
                    }
                    khachhangtable.setModel(modelKhachhang);
                
               
            }
            if("refreshnv".equals(e.getActionCommand()))
            {
                seachtxt.setText("");
                modelNhanvien.setRowCount(0);
                for (AccountDTO acc : AccountBUS.Arr_account){
                        Vector tempp = new Vector();
                            tempp.add(acc.getId_nhanvien());
                            tempp.add(acc.getHoten());
                            tempp.add(acc.getNgaysinh());
                            tempp.add(acc.getGioitinh());
                            tempp.add(acc.getDiachi());
                            tempp.add(acc.getSodienthoai());
                            dataNV = tempp;
                            modelNhanvien.addRow(dataNV);
                        
                    }
                nhanvienTable.setModel(modelNhanvien);     
               
            }
            if("refreshtk".equals(e.getActionCommand()))
            {
                seachtxt.setText("");
                modelTaiKhoan.setRowCount(0);
                    for (LoginDTO login : LoginBUS.Arr_login){
                        Vector temp = new Vector();
                            temp.add(login.getId());
                            temp.add(login.getUsername());
                            temp.add(login.getPassword());
                            temp.add(login.getType());
                            dataTK = temp;
                            modelTaiKhoan.addRow(dataTK);
                        
                    }
                    accountTabel.setModel(modelTaiKhoan);         
            }
            
            if("print".equals(e.getActionCommand()))
            { 
                try {
                    new HoaDonGUI();
                } catch (Exception ex) {
                    Logger.getLogger(HomeUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if("combobox".equals(e.getActionCommand())){
                Vector temp = SanPhamBUS.CreateComboBOX();
                model.setRowCount(0); 
                for(int i = 0 ; i < temp.size();i++){
                    if(loaiSanPhamCBB.getSelectedItem().toString() == temp.get(i)){
                        Vector tempLoai = new Vector(SanPhamBUS.timKiemTheoLoai(temp.get(i).toString()));
                        for (int j =0; j < tempLoai.size();i++) {
                            Add_row_SanPham((ProductsDTO) tempLoai.get(j));
                        }
                    }
                }
                    
//                    if(loaiSanPhamCBB.getSelectedItem().toString() == temp.get(1)){
//                        Vector tempLoai = new Vector(SanPhamBUS.timKiemTheoLoai(temp.get(1).toString()));
//                        for (int i =0; i < tempLoai.size();i++) {
//                            Add_row_SanPham((ProductsDTO) tempLoai.get(i));
//                        }
//                    }
//                    if(loaiSanPhamCBB.getSelectedItem().toString() == temp.get(2)){
//                        Vector tempLoai = new Vector(SanPhamBUS.timKiemTheoLoai(temp.get(2).toString()));
//                        for (int i =0; i < tempLoai.size();i++) {
//                            Add_row_SanPham((ProductsDTO) tempLoai.get(i));
//                        }
//                    }
                    
            }
            if("xoaARRSP".equals(e.getActionCommand()))
            {
               int  i=gioHangTable.getSelectedRow();
               ProductsDTO product = new ProductsDTO();
                if(i>=0)
                { 
                    product = Arr_GioHang.get(i);
                    Arr_GioHang.remove(i);
                }
                modelGioHang.setRowCount(0);
                for (int j =0; j < Arr_GioHang.size();j++) {
                    Add_row_GioHang(Arr_GioHang.get(j));
                }
                
            }
            if("exe".equals(e.getActionCommand()))
            {
                try {
                    String gtBDtemp = giaTriDauTXT.getText();
                    String gtKTtemp = giaTriCuoiTXT.getText();
                    double gtBD = Double.parseDouble(gtBDtemp);
                    double gtKT = Double.parseDouble(gtKTtemp);
                    if(gtBD >= gtKT){
                        JOptionPane.showMessageDialog(this, "Giá trị nhập không hợp lệ! Vui lòng thử lại!");
                        giaTriDauTXT.setText("");
                        giaTriCuoiTXT.setText("");
                    }
                    else{
                        model.setRowCount(0);
                        for (int i =0; i < SanPhamBUS.timkiemTheoGia(Double.valueOf(gtBD), Double.valueOf(gtKT)).size();i++) {
                            Add_row_SanPham(SanPhamBUS.timkiemTheoGia(Double.valueOf(gtBD), Double.valueOf(gtKT)).get(i));
                        }
                    }
                } catch (Exception ev) {
                    JOptionPane.showMessageDialog(this, "Giá trị nhập không hợp lệ! Vui lòng thử lại!");
                    giaTriDauTXT.setText("");
                        giaTriCuoiTXT.setText("");
                }               
               
               
            }
            if("+".equals(e.getActionCommand()))
            {
                count++;
                countTXT.setText(String.valueOf(count));
                if(count == 10){
                    int input = JOptionPane.showConfirmDialog(this, "MAY BOM HANG A????");
                    if(input == 0){
                        JOptionPane.showMessageDialog(rootPane, "CUTTTTTTTTTT");
                }
                    if(input == 1){
                        JOptionPane.showMessageDialog(rootPane, "À ừ thì xin lỗi");
                    }
                }
                SanPhamBUS BUS = new SanPhamBUS();
                try {
                    BUS.docSanPham();           
                } catch (Exception ex) {
                    Logger.getLogger(HomeUser.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
            if("-".equals(e.getActionCommand()))
            {
                if(count>1){
                    count--;
                    countTXT.setText(String.valueOf(count));
                }          
            }
            //-------------------------------------end SP type 0
            //---------------------------Tai khoan admin
            
            if ("confirm".equals(e.getActionCommand())){
                AccountDAO update = new AccountDAO();
//                account.hoten = this.menuTextField[0].getText();
//                account.ngaysinh = this.menuTextField[1].getText();
//                account.gioitinh = this.menuTextField[2].getText();
//                account.sodienthoai = this.menuTextField[3].getText();
                String hoten = this.menuTextField[0].getText();
                String ngaysinh = this.menuTextField[1].getText();
                String gioitinh = this.menuTextField[2].getText();
                String sodienthoai = this.menuTextField[3].getText();
                String diachi = this.menuTextField[4].getText();
                String id = Login.idhienhanh;
                update.updateThongtin(hoten, ngaysinh, gioitinh, sodienthoai, id, diachi);
                JOptionPane.showMessageDialog(this, "Thay đổi thông tin thành công");
                try {
                    account_bus.docaccount();
                    for (AccountDTO account : AccountBUS.Arr_account){
                        if (Login.idhienhanh.equals(account.getId_nhanvien())){
                            menuTextField[0].setText(account.hoten);
                            menuTextField[1].setText(account.ngaysinh);
                            menuTextField[2].setText(account.gioitinh);
                            menuTextField[3].setText(account.sodienthoai);
                            menuTextField[4].setText(account.diachi);
                            break;
                        }
                        
                    }
                } catch (Exception ae){
                }
            }
            if ("suataikhoan".equals(e.getActionCommand())){
                LoginDAO update = new LoginDAO();
                String taikhoan = this.taikhoan_TextField.getText();
                String matkhau = this.matkhau_TextField.getText();
                String id = this.id_TextField.getText();
                update.updateTaikhoan(taikhoan,matkhau,id);
                JOptionPane.showMessageDialog(this,"Sửa thông tin thành công!");
                taikhoan_TextField.setText(null);
                matkhau_TextField.setText(null);
                id_TextField.setText(null);
                type_TextField.setText(null);
                modelTaiKhoan.setRowCount(0);
                
                try {
                    login_bus.docdangnhap();
                    
                    for (LoginDTO login : LoginBUS.Arr_login){
                        Vector temp = new Vector();
                            temp.add(login.getId());
                            temp.add(login.getUsername());
                            temp.add(login.getPassword());
                            temp.add(login.getType());
                            dataTK = temp;
                            modelTaiKhoan.addRow(dataTK);
                            System.out.println(dataTK);
                        
                    }
                    accountTabel.setModel(modelTaiKhoan);
                } catch (Exception ae){
                }
                   
            }
            if ("themtaikhoan".equals(e.getActionCommand())){
                LoginDAO update = new LoginDAO();
                String taikhoan = this.taikhoan_TextField.getText();
                String matkhau = this.matkhau_TextField.getText();
                String id = this.id_TextField.getText();
                String type = this.type_TextField.getText();
                if (id.equals("") || matkhau.equals("") || taikhoan.equals("") || type.equals(""))      
                        JOptionPane.showMessageDialog(this,"Vui lòng nhập đầy đủ thông tin vào ô trống");
                    else{
                        if (LoginBUS.checkID(id_TextField.getText(), LoginBUS.Arr_login) || LoginBUS.checkUsername(taikhoan_TextField.getText(), LoginBUS.Arr_login))
                            JOptionPane.showMessageDialog(this,"ID hoặc tài khoản đã tồn tại");
                        else{
                        try{
                            update.addTaikhoan(taikhoan, matkhau, id, type);
                            JOptionPane.showMessageDialog(this,"Thêm tài khoản thành công!");
                            taikhoan_TextField.setText(null);
                            matkhau_TextField.setText(null);
                            id_TextField.setText(null);
                            type_TextField.setText(null);
                            modelTaiKhoan.setRowCount(0);
                            login_bus.docdangnhap();
                            for (LoginDTO login : LoginBUS.Arr_login){
                                Vector temp = new Vector();
                                temp.add(login.getId());
                                temp.add(login.getUsername());
                                temp.add(login.getPassword());
                                temp.add(login.getType());
                                dataTK = temp;
                                modelTaiKhoan.addRow(dataTK);
                            }
                        accountTabel.setModel(modelTaiKhoan);
                        } catch (Exception ae){
                        }
                        }
                    } 
                   
            }
            if ("xoataikhoan".equals(e.getActionCommand())){
                LoginDAO update = new LoginDAO();
                String taikhoan = this.taikhoan_TextField.getText();
                String matkhau = this.matkhau_TextField.getText();
                String id = this.id_TextField.getText();
                String type = this.type_TextField.getText();
                update.deleteTaikhoan(taikhoan,matkhau,id,type);
                JOptionPane.showMessageDialog(this,"Xoá thông tin thành công!");
                taikhoan_TextField.setText(null);
                matkhau_TextField.setText(null);
                id_TextField.setText(null);
                type_TextField.setText(null);
                modelTaiKhoan.setRowCount(0);
                
                try {
                    login_bus.docdangnhap();
                    
                    for (LoginDTO login : LoginBUS.Arr_login){
                        Vector temp = new Vector();
                            temp.add(login.getId());
                            temp.add(login.getUsername());
                            temp.add(login.getPassword());
                            temp.add(login.getType());
                            dataTK = temp;
                            modelTaiKhoan.addRow(dataTK);
                            System.out.println(dataTK);
                        
                    }
                    accountTabel.setModel(modelTaiKhoan);
                } catch (Exception ae){
                }
                   
            }
            if ("suanhanvien".equals(e.getActionCommand())){
                AccountDAO update = new AccountDAO();
                String hoten = this.hoten_TextField.getText();
                String ngaysinh = this.ngaysinh_TextField.getText();
                String gioitinh = this.gioitinh_TextField.getText();
                String diachi = this.diachi_TextField.getText();
                String sdt = this.sdt_TextField.getText();
                String id = this.idnhanvien_TextField.getText();
                update.updateNhanvien(hoten,ngaysinh,gioitinh,diachi,sdt,id);
                JOptionPane.showMessageDialog(this,"Sửa thông tin thành công!");
                hoten_TextField.setText(null);
                ngaysinh_TextField.setText(null);
                idnhanvien_TextField.setText(null);
                gioitinh_TextField.setText(null);
                diachi_TextField.setText(null);
                sdt_TextField.setText(null);
                modelNhanvien.setRowCount(0); 
                try {
                    account_bus.docaccount();
                    for (AccountDTO acc : AccountBUS.Arr_account){
                        Vector tempp = new Vector();
                            tempp.add(acc.getId_nhanvien());
                            tempp.add(acc.getHoten());
                            tempp.add(acc.getNgaysinh());
                            tempp.add(acc.getGioitinh());
                            tempp.add(acc.getDiachi());
                            tempp.add(acc.getSodienthoai());
                            dataNV = tempp;
                            modelNhanvien.addRow(dataNV);
                        
                    }
                    nhanvienTable.setModel(modelNhanvien);
                } catch (Exception ae){
                }
            }
            if ("xoanhanvien".equals(e.getActionCommand())){
                AccountDAO update = new AccountDAO();
                String hoten = this.hoten_TextField.getText();
                String ngaysinh = this.ngaysinh_TextField.getText();
                String gioitinh = this.gioitinh_TextField.getText();
                String diachi = this.diachi_TextField.getText();
                String sdt = this.sdt_TextField.getText();
                String id = this.idnhanvien_TextField.getText();
                update.deleteNhanvien(hoten,ngaysinh,gioitinh,diachi,sdt,id);
                JOptionPane.showMessageDialog(this,"Xoá thông tin thành công!");
                hoten_TextField.setText(null);
                ngaysinh_TextField.setText(null);
                idnhanvien_TextField.setText(null);
                gioitinh_TextField.setText(null);
                diachi_TextField.setText(null);
                sdt_TextField.setText(null);
                modelNhanvien.setRowCount(0); 
                try {
                    account_bus.docaccount();
                    for (AccountDTO acc : AccountBUS.Arr_account){
                        Vector tempp = new Vector();
                            tempp.add(acc.getId_nhanvien());
                            tempp.add(acc.getHoten());
                            tempp.add(acc.getNgaysinh());
                            tempp.add(acc.getGioitinh());
                            tempp.add(acc.getDiachi());
                            tempp.add(acc.getSodienthoai());
                            dataNV = tempp;
                            modelNhanvien.addRow(dataNV);
                        
                    }
                    nhanvienTable.setModel(modelNhanvien);
                } catch (Exception ae){
                }
            }
            if ("themnhanvien".equals(e.getActionCommand())){
                AccountDAO update = new AccountDAO();
                String hoten = this.hoten_TextField.getText();
                String ngaysinh = this.ngaysinh_TextField.getText();
                String gioitinh = this.gioitinh_TextField.getText();
                String diachi = this.diachi_TextField.getText();
                String sdt = this.sdt_TextField.getText();
                String id = this.idnhanvien_TextField.getText();
                if (id.equals("") || hoten.equals("") || sdt.equals("") || ngaysinh.equals("") || gioitinh.equals("") || diachi.equals(""))      
                        JOptionPane.showMessageDialog(this,"Vui lòng nhập đầy đủ thông tin vào ô trống");
                    else{
                        if (AccountBUS.checkID(idnhanvien_TextField.getText(), AccountBUS.Arr_account))
                            JOptionPane.showMessageDialog(this,"ID nhân viên đã tồn tại");
                        else{
                        try{
                            update.addNhanvien(hoten, ngaysinh, gioitinh, diachi, sdt, id);
                            JOptionPane.showMessageDialog(this,"Thêm nhân viên thành công!");
                            hoten_TextField.setText(null);
                            ngaysinh_TextField.setText(null);
                            gioitinh_TextField.setText(null);
                            diachi_TextField.setText(null);
                            sdt_TextField.setText(null);
                            idnhanvien_TextField.setText(null);
                            modelNhanvien.setRowCount(0);
                            account_bus.docaccount();
                            for (AccountDTO account : AccountBUS.Arr_account){
                                Vector temp = new Vector();
                                temp.add(account.getId_nhanvien());
                                temp.add(account.getHoten());
                                temp.add(account.getNgaysinh());
                                temp.add(account.getGioitinh());
                                temp.add(account.getSodienthoai());
                                temp.add(account.getDiachi());
                                dataNV = temp;
                                modelNhanvien.addRow(dataNV);
                            }
                        nhanvienTable.setModel(modelNhanvien);
                        } catch (Exception ae){
                        }
                        }
                    } 
            }
            
            if ("suakhachhang".equals(e.getActionCommand())){
                KhachHangDAO update = new KhachHangDAO();
                String id = this.idkh_TextField.getText();
                String hoten = this.hotenkh_TextField.getText();
                String sdt = this.sdtkh_TextField.getText();
                String gmail = this.gmail_TextField.getText();
                update.updateKhachhang(id,hoten,sdt,gmail);
                JOptionPane.showMessageDialog(this,"Sửa thông tin thành công!");
                hotenkh_TextField.setText(null);
                idkh_TextField.setText(null);
                sdtkh_TextField.setText(null);
                gmail_TextField.setText(null);
                modelKhachhang.setRowCount(0);
                try {
                    khachhang_bus.docKhachHang();
                    for (KhachHangDTO khachhang : KhachHangBUS.Arr_khachhang){
                        Vector temp = new Vector();
                            temp.add(khachhang.getID_Khachhang());
                            temp.add(khachhang.getTenKhachHang());
                            temp.add(khachhang.getSDT());
                            temp.add(khachhang.getGmail());
                            dataKH = temp;
                            modelKhachhang.addRow(dataKH);
                        
                    }
                    khachhangtable.setModel(modelKhachhang);
                } catch (Exception ae){
                }
            }
            if ("themkhachhang".equals(e.getActionCommand())){
                KhachHangDAO update = new KhachHangDAO();
                String id = this.idkh_TextField.getText();
                String hoten = this.hotenkh_TextField.getText();
                String sdt = this.sdtkh_TextField.getText();
                String gmail = this.gmail_TextField.getText();
                    if (id.equals("") || hoten.equals("") || sdt.equals("") || gmail.equals(""))      
                        JOptionPane.showMessageDialog(this,"Vui lòng nhập đầy đủ thông tin vào ô trống");
                    else{
                        if (KhachHangBUS.checkID(idkh_TextField.getText(), KhachHangBUS.Arr_khachhang))
                            JOptionPane.showMessageDialog(this,"ID khách hàng đã tồn tại");
                        else{
                        try{
                            update.addKhachhang(id,hoten,sdt,gmail);
                            JOptionPane.showMessageDialog(this,"Thêm khách hàng thành công!");
                            hotenkh_TextField.setText(null);
                            idkh_TextField.setText(null);
                            sdtkh_TextField.setText(null);
                            gmail_TextField.setText(null);
                            modelKhachhang.setRowCount(0);
                            khachhang_bus.docKhachHang();
                            for (KhachHangDTO khachhang : KhachHangBUS.Arr_khachhang){
                                Vector temp = new Vector();
                                temp.add(khachhang.getID_Khachhang());
                                temp.add(khachhang.getTenKhachHang());
                                temp.add(khachhang.getSDT());
                                temp.add(khachhang.getGmail());
                                dataKH = temp;
                                modelKhachhang.addRow(dataKH);
                            }
                        khachhangtable.setModel(modelKhachhang);
                        } catch (Exception ae){
                        }
                        }
                    }
                }
            if ("xoakhachhang".equals(e.getActionCommand())){
                KhachHangDAO update = new KhachHangDAO();
                String id = this.idkh_TextField.getText();
                String hoten = this.hotenkh_TextField.getText();
                String sdt = this.sdtkh_TextField.getText();
                String gmail = this.gmail_TextField.getText();
                update.deleteKhachhang(id,hoten,sdt,gmail);
                JOptionPane.showMessageDialog(this,"Sửa thông tin thành công!");
                hotenkh_TextField.setText(null);
                idkh_TextField.setText(null);
                sdtkh_TextField.setText(null);
                gmail_TextField.setText(null);
                modelKhachhang.setRowCount(0);
                try {
                    khachhang_bus.docKhachHang();
                    for (KhachHangDTO khachhang : KhachHangBUS.Arr_khachhang){
                        Vector temp = new Vector();
                            temp.add(khachhang.getID_Khachhang());
                            temp.add(khachhang.getTenKhachHang());
                            temp.add(khachhang.getSDT());
                            temp.add(khachhang.getGmail());
                            dataKH = temp;
                            modelKhachhang.addRow(dataKH);
                        
                    }
                    khachhangtable.setModel(modelKhachhang);
                } catch (Exception ae){
                }
            }
              
            if ("QLNVExcel".equals(e.getActionCommand())){
                    ExportExcel abc = new ExportExcel();
                    String input = JOptionPane.showInputDialog("Đặt tên cho Table");
                    abc.ExportExcel(input, nhanvienTable);
            }
            if ("QLTKExcel".equals(e.getActionCommand())){
                    ExportExcel abc = new ExportExcel();
                    String input = JOptionPane.showInputDialog("Đặt tên cho Table");
                    abc.ExportExcel(input, accountTabel);
            }
            if ("QLKHExcel".equals(e.getActionCommand())){
                    ExportExcel abc = new ExportExcel();
                    String input = JOptionPane.showInputDialog("Đặt tên cho Table");
                    abc.ExportExcel(input, khachhangtable);
            }
            
            //////////////////---------------------san pham TYPE = 1 admin
            if ("addADMIN".equals(e.getActionCommand())) {
                ProductsDTO products =new ProductsDTO();
                String duongDan = "/Image/";
                    if(SanPhamBUS.kt_trung_ma(menuTextFieldADMIN[0].getText(), SanPhamBUS.Arr_products)
                            ||menuTextFieldADMIN[0].getText().equals("")){
                       JOptionPane.showMessageDialog(this, "Mã sản phẩm không hợp lệ! Vui lòng nhập lại");
                    }else{
                        products.setID_Product(menuTextFieldADMIN[0].getText());
                        products.setName(menuTextFieldADMIN[1].getText());
                        products.setPice(Double.valueOf(menuTextFieldADMIN[2].getText()));
                        products.setCategory(menuTextFieldADMIN[3].getText());
                        products.setTrangThai(Integer.valueOf(menuTextFieldADMIN[4].getText()));
                        products.setImg_path(duongDan+menuTextFieldADMIN[5].getText());
                        products.setAmount(0);
                        try {
                            SanPhamBUS.themSanPham(products);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        for(int j = 0 ; j < menuTextFieldADMIN.length ; j ++){
                            menuTextFieldADMIN[j].setText("");
                        }
                        model.setRowCount(0);
                            for (ProductsDTO product : SanPhamBUS.Arr_products){
                               if(product.getTrangThai() == 1)
                                    Add_row_SanPham(product);
                            }
                            productTable.setModel(model);
                    }           

        }
        if ("xoaADMIN".equals(e.getActionCommand())) {
            ProductsDTO products =new ProductsDTO();
                Common.trangThai = products;
                int  i=productTable.getSelectedRow();
                String duongDan = "/Image/";
                products.setID_Product(menuTextFieldADMIN[0].getText());
                products.setName(menuTextFieldADMIN[1].getText());
                products.setPice(Double.valueOf(menuTextFieldADMIN[2].getText()));
                products.setCategory(menuTextFieldADMIN[3].getText());
                products.setImg_path(duongDan+menuTextFieldADMIN[4].getText());
                products.setTrangThai(0);
                products.setAmount(0);
                if (JOptionPane.showConfirmDialog(null, "Xoá sản Phẩm?", "Tiếp Tục",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {      
                    try {
                         SanPhamBUS.xoaSanPham(products);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
                for(int j = 0 ; j < menuTextFieldADMIN.length ; j++){
                    menuTextFieldADMIN[j].setText("");
                }
                model.setRowCount(0);
                try {
                    SanPhamBUS.docSanPham();
                    
                    for (ProductsDTO product : SanPhamBUS.Arr_products){
                        if(product.getTrangThai() == 1)
                            Add_row_SanPham(product);
                    }
                    productTable.setModel(model);
                } catch (Exception ae){
                }
        }
        if ("suaADMIN".equals(e.getActionCommand())) {
                ProductsDTO products =new ProductsDTO();
                int  i=productTable.getSelectedRow();
                String duongDan = "/Image/";
                products.setID_Product(menuTextFieldADMIN[0].getText());
                products.setName(menuTextFieldADMIN[1].getText());
                products.setPice(Double.valueOf(menuTextFieldADMIN[2].getText()));
                products.setCategory(menuTextFieldADMIN[3].getText());
                products.setTrangThai(Integer.valueOf(menuTextFieldADMIN[4].getText()));
                products.setImg_path(duongDan+menuTextFieldADMIN[5].getText());
                products.setAmount(0);
                
                SanPhamBUS.suaSanPham(products);
                model.setRowCount(0);
                for(int j = 0 ; j < menuTextFieldADMIN.length ; j++){
                    menuTextFieldADMIN[j].setText("");
                }
                try {
                    SanPhamBUS.docSanPham();
                    for (ProductsDTO product : SanPhamBUS.Arr_products){
                        if(product.getTrangThai() == 1)
                            Add_row_SanPham(product);
                    }
                    productTable.setModel(model);
                } catch (Exception ae){
                }
        }
        if ("xuatEXCEL".equals(e.getActionCommand())) {
            ExportExcel ab = new ExportExcel();
           String input = JOptionPane.showInputDialog("Chọn Tên bảng trong Excel:");
            ab.ExportExcel(input, productTable);
        }
        if ("report".equals(e.getActionCommand())) {
        }
        //-------------------------------end Sp admin
        //----------------hoadonADMIN
        if ("anhoadon".equals(e.getActionCommand())) {
            int i = hoaDonTable.getSelectedRow();
            HoaDonDTO hoadon = HoaDonBUS.Arr_HoaDonBUS.get(i);
            if(i>=0){
                hoadon.setTrangThai(0);
                HoaDonBUS.suaHoaDon(hoadon);
                modelHoadon.setRowCount(0);
                try {
                    HoaDonBUS.docHoaDon();
                    for(HoaDonDTO hoadons : HoaDonBUS.Arr_HoaDonBUS){
                            Add_row_Hoadon(hoadons);
                    }
                    hoaDonTable.setModel(modelHoadon);
                } catch (Exception ae){
                }
            }
        }
        if ("chitiethoadon".equals(e.getActionCommand())) {
                try {
                    new ChiTietHoaDonGUI();
                } catch (Exception ex) {
                    Logger.getLogger(HomeUser.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if ("format".equals(e.getActionCommand())) {
        }
        if("findHD".equals(e.getActionCommand()))
            {
                String temp = seachtxt.getText();
                modelHoadon.setRowCount(0);
                try {
                    HoaDonBUS.docHoaDon();
                    for (HoaDonDTO hoadon : HoaDonBUS.findHoadon(temp)){
                        Vector tempp = new Vector();
                            tempp.add(hoadon.getID_Hoadon());
                            tempp.add(hoadon.getID_NhanVien());
                            tempp.add(hoadon.getID_Khachhang());
                            tempp.add(hoadon.getID_Giamgia());
                            tempp.add(hoadon.getNgaylap());
                            tempp.add(hoadon.getThanhTien());
                            tempp.add(hoadon.getTrangThai());
                            dataKH = tempp;
                            modelHoadon.addRow(dataKH); 
                    }
                hoaDonTable.setModel(modelHoadon);
                }catch(Exception ae){
                    
                }
                
            }
        if("refreshHD".equals(e.getActionCommand()))
        {
             seachtxt.setText("");
            modelHoadon.setRowCount(0);
            for (int i =0; i < HoaDonBUS.Arr_HoaDonBUS.size();i++) {
                Add_row_Hoadon(HoaDonBUS.Arr_HoaDonBUS.get(i));
            }    
        }
        if ("hoadonexcel".equals(e.getActionCommand())) {
            ExportExcel ab = new ExportExcel();
           String input = JOptionPane.showInputDialog("Chọn Tên bảng trong Excel:");
            ab.ExportExcel(input, hoaDonTable);
        }
        if ("hoadonreport".equals(e.getActionCommand())) {
        }
        //--------------------end hoa don
        //-------------------thong ke hoa 
        if ("xuatExcelThongKe".equals(e.getActionCommand())) {
            ExportExcel ab = new ExportExcel();
            String input = JOptionPane.showInputDialog("Chọn Tên bảng trong Excel:");
            ab.ExportExcel(input, thongKeHDTable);
        }
        if ("loc".equals(e.getActionCommand())) {
            //-----------cach 1
            //get ngay bat dau
            Date tempBD = Date.valueOf(df.format(ngayBatDauDate.getDate()));
            long ngayBDTIME = tempBD.getTime();
            
            //get ngay Ket thuc
            Date tempKT = Date.valueOf(df.format(ngayKetThucDate.getDate()));
            long ngayKTTIME = tempKT.getTime();
            //-----------------------
            //cat chuoi - cach 2
                Vector ngayBDVecTor = HoaDonBUS.splitDAY(df.format(ngayBatDauDate.getDate()));

                int namBD= Integer.valueOf(ngayBDVecTor.get(0).toString());
                int thangBD = Integer.valueOf(ngayBDVecTor.get(1).toString());
                int ngayBD = Integer.valueOf(ngayBDVecTor.get(2).toString());

                Vector ngayKTVecTor = HoaDonBUS.splitDAY(df.format(ngayKetThucDate.getDate()));

                int namKT = Integer.valueOf(ngayKTVecTor.get(0).toString());
                int thangKT = Integer.valueOf(ngayKTVecTor.get(1).toString());
                int ngayKT = Integer.valueOf(ngayKTVecTor.get(2).toString());
            //if(ngayBD >= ngayKT && thangBD >= thangKT && namBD >= namKT)
            //-----------------------------
                if(ngayBDTIME >= ngayKTTIME){
                    JOptionPane.showMessageDialog(this, "Thời gian bắt đầu KHÔNG thể lớn hơn Thời gian kết thúc! Vui lòng Nhập Lại !!!");
                }else{
                    modelThongKeHD.setRowCount(0);
                    for(int i = 0 ; i < HoaDonBUS.check(ngayBDTIME,ngayKTTIME).size() ; i++){
                        TongTienTheoNgay += HoaDonBUS.check(ngayBDTIME, ngayKTTIME).get(i).getThanhTien();
                        Add_row_ThongKeHD(HoaDonBUS.check(ngayBDTIME,ngayKTTIME).get(i));
                    }
                    thongKeHDTable.setModel(modelThongKeHD);
                }
            tongTienLabel.setText("Tổng doanh thu (Từ Ngày: "+tempBD+" Đến "+tempKT+"):");
            tongTienTheoNgayLabel.setText((int)TongTienTheoNgay+" VNĐ");
        }
        //------------------end
    }
  
}
