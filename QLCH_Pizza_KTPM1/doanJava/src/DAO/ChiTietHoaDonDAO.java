/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.ChiTietHoaDonDTO;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author nguye
 */
public class ChiTietHoaDonDAO {
    MySQLConnect connect = new MySQLConnect("localhost", "root", "", "pizza");
        public ArrayList<ChiTietHoaDonDTO> docchitiethoadon() throws Exception{
            ChiTietHoaDonDTO chitietHD = new ChiTietHoaDonDTO();
            ArrayList<ChiTietHoaDonDTO> Arr_ChiTietHoaDon = new ArrayList();
            String query = "SELECT * FROM chitiethoadon" +
            " GROUP BY ID_Product";
            Statement st = connect.getStatement();
            ResultSet rs = st.executeQuery(query);
            try {
                while (rs.next()) {
                chitietHD.setID_Hoadon(rs.getString("ID_Hoadon"));
                chitietHD.setID_Product(rs.getString("ID_Product"));
                chitietHD.setID_Khachhang(rs.getString("ID_Khachhang"));
                chitietHD.setSoLuong(rs.getInt("SoLuong"));
                chitietHD.setThanhTien(rs.getDouble("ThanhTien"));
                chitietHD.setNgayLap(rs.getString("NgayLap"));
                Arr_ChiTietHoaDon.add(chitietHD);          
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Lỗi đọc danh sách");
        }
            rs.close();

            return Arr_ChiTietHoaDon;
        }
    }
