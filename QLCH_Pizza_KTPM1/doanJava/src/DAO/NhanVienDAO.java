/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.NhanVienDTO;
import DTO.NhanVienDTO;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author nguye
 */
public class NhanVienDAO {
    ArrayList<NhanVienDTO> Arr_Nhanvien = new ArrayList();
    public static int soIDNhanVien;
    MySQLConnect connect = new MySQLConnect("localhost", "root", "", "pizza");
    public ArrayList<NhanVienDTO> docNhanVien() throws Exception{
        //connect      
        String query = "SELECT * From nhanvien ";
        Statement st = connect.getStatement();
        ResultSet rs = st.executeQuery(query);
        soIDNhanVien = connect.CountRow(rs);
        try {
            while (rs.next()) {
                NhanVienDTO nhanvien = new NhanVienDTO();
                nhanvien.setID_NhanVien(rs.getString("ID_NhanVien"));
                nhanvien.setTenNhanVien(rs.getString("TenNhanVien"));
                nhanvien.setNgaySinh(rs.getString("NgaySinh"));
                nhanvien.setGioiTinh(rs.getString("GioiTinh"));
                nhanvien.setDiaChi(rs.getString("DiaChi"));
                nhanvien.setSDT(rs.getString("SDT"));
                Arr_Nhanvien.add(nhanvien);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Lỗi đọc danh sách");
        }
        rs.close();
        return Arr_Nhanvien;
    }
    
    public void them(NhanVienDTO nv){
         try{
            String qry ="INSERT into nhanvien values (";
            qry = qry+"'"+nv.getID_NhanVien()+"'";
            qry = qry+","+"'"+nv.getTenNhanVien()+"'";
            qry = qry+","+"'"+nv.getNgaySinh()+"'";
            qry = qry+","+"'"+nv.getGioiTinh()+"'";
            qry = qry+","+"'"+nv.getDiaChi()+"'";
            qry = qry+","+"'"+nv.getSDT()+"'";
             qry = qry+")";
            connect.getStatement();
            connect.executeQuery(qry);
            JOptionPane.showMessageDialog(null,"Thêm Thành Công");
            connect.Close();
       } catch (Exception ex) {
           ex.printStackTrace();
           JOptionPane.showMessageDialog(null,"Lỗi");
       }       
    }
    public void xoa(NhanVienDTO nv){
        try{
            String qry="Update nhanvien Set ";
            qry = qry+"WHERE ID_NhanVien='"+nv.getID_NhanVien()+"'";
            connect.getStatement();
            connect.executeUpdate(qry);
            JOptionPane.showMessageDialog(null,"Xoá Thành Công");
            connect.Close();
                    
        }catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Lỗi");
        }
    }
    public void sua(NhanVienDTO nv){
        try{
            String qry="Update nhanvien Set ";
            qry = qry+"TenNhanVien="+"'"+nv.getTenNhanVien()+"',";
            qry = qry+"NgaySinh="+"'"+nv.getNgaySinh()+"',";
            qry = qry+"GioiTinh="+"'"+nv.getGioiTinh()+"',";
            qry = qry+"DiaChi="+"'"+nv.getDiaChi()+"',";
            qry = qry+"SDT="+"'"+nv.getSDT()+"',";
            qry = qry+"WHERE ID_NhanVien='"+nv.getID_NhanVien()+"'";
            connect.getStatement();
            connect.executeUpdate(qry);
            JOptionPane.showMessageDialog(null,"sửa Thành Công");
            connect.Close();
                    
        }catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Lỗi");
        }
    }
}
