/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import DTO.NhanVienDTO;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class NhanVienBUS {
    public  static  ArrayList<NhanVienDTO> Arr_Nhanvien = new ArrayList();
    
    public  void DocNhanVien() throws Exception
    {
        NhanVienDAO data = new NhanVienDAO();
        if(Arr_Nhanvien == null)
            Arr_Nhanvien = new ArrayList<>();
        Arr_Nhanvien = data.docNhanVien();
        //ddd
    }
    public static ArrayList<NhanVienDTO> findNhanvien(String tuKhoa) {
        ArrayList<NhanVienDTO> Arr_temp = new ArrayList<>();
            for (int i = 0; i< Arr_Nhanvien.size() ;i++) {
                if(Arr_Nhanvien.get(i).getID_NhanVien().toLowerCase().contains(tuKhoa.toLowerCase())||
                Arr_Nhanvien.get(i).getTenNhanVien().toLowerCase().contains(tuKhoa.toLowerCase())||
                Arr_Nhanvien.get(i).getNgaySinh().toLowerCase().contains(tuKhoa.toLowerCase())||
                Arr_Nhanvien.get(i).getGioiTinh().toLowerCase().contains(tuKhoa.toLowerCase())||
                Arr_Nhanvien.get(i).getSDT().toLowerCase().contains(tuKhoa.toLowerCase())||
                Arr_Nhanvien.get(i).getDiaChi().toLowerCase().contains(tuKhoa.toLowerCase()))
                    Arr_temp.add(Arr_Nhanvien.get(i));           
            }
        return Arr_temp;
    }
    
    public void ThemNhanVien(NhanVienDTO nv)
    {
        NhanVienDAO data = new NhanVienDAO();
        data.them(nv);//gọi hàm thêm bên DAO để thêm sách vào database
        Arr_Nhanvien.add(nv);//
    }
    public void SuaNhanVien(NhanVienDTO nv)
    {
        NhanVienDAO data=new NhanVienDAO();
        data.sua(nv);
        Arr_Nhanvien.add(nv);
    }
    public void XoaNhanVien(NhanVienDTO nv)
    {
        NhanVienDAO data=new NhanVienDAO();
        data.xoa(nv);
        Arr_Nhanvien.add(nv);
    }
}