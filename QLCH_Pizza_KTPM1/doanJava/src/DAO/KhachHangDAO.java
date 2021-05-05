/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.KhachHangDTO;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author nguye
 */
public class KhachHangDAO {
    ArrayList<KhachHangDTO> Arr_khachhang = new ArrayList();
    MySQLConnect connect = new MySQLConnect("localhost", "root", "", "pizza");
    public ArrayList<KhachHangDTO> docKhachHang() throws Exception{
        //connect
        

        String query = "SELECT * From khachhang ";
        Statement st = connect.getStatement();
        ResultSet rs = st.executeQuery(query);
        try {
                    while (rs.next()) {
                KhachHangDTO khachhang = new KhachHangDTO();
                khachhang.setID_Khachhang(rs.getString("ID_Khachhang"));
                khachhang.setTenKhachHang(rs.getString("TenKhachHang"));
                khachhang.setSDT(rs.getString("SDT"));
                khachhang.setGmail(rs.getString("Gmail"));    
                Arr_khachhang.add(khachhang);     
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Lỗi đọc danh sách");
        }

        rs.close();
       
        return Arr_khachhang;
    }
    public void updateKhachhang(String id, String hoten, String sdt, String gmail) {
        try{           
                MySQLConnect connect = new MySQLConnect("localhost", "root", "", "pizza");
                Statement st = connect.getStatement();
                
                String sql = "UPDATE khachhang "
                        + "SET `Gmail`= \"" + gmail + "\", "
                        + "TenKhachHang = \"" + hoten + "\", "
                        + "SDT = \"" + sdt     + "\" "
                        + "WHERE `ID_Khachhang`= \"" + id + "\" ";
                st.executeUpdate(sql);
            }
            catch (Exception e){ 
            }
    }
    public void addKhachhang(String id, String hoten, String sdt, String gmail) {
        try{           
                MySQLConnect connect = new MySQLConnect("localhost", "root", "", "pizza");
                Statement st = connect.getStatement();
                
                String sql = "INSERT INTO khachhang (ID_Khachhang, TenKhachHang, SDT, Gmail) VALUES ("
                        + "\"" + id + "\""
                        + ",\"" + hoten + "\""
                        + ",\"" + sdt + "\""
                        + ",\"" + gmail + "\")";
                st.executeUpdate(sql);
            }
            catch (Exception e){ 
            }
    }
    public void deleteKhachhang(String id, String hoten, String sdt, String gmail) {
        try{           
                MySQLConnect connect = new MySQLConnect("localhost", "root", "", "pizza");
                Statement st = connect.getStatement();
                
                String sql = "DELETE FROM khachhang WHERE "
                        + "ID_Khachhang = \"" + id + "\"";
                System.out.println(sql);
                st.executeUpdate(sql);
            }
            catch (Exception e){ 
            }
    }
}