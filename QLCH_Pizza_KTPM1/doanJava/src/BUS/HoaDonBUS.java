/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.HoaDonDAO;
import DTO.HoaDonDTO;
import GUI.HoaDonGUI;
import GUI.HomeUser;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author nguye
 */
public class HoaDonBUS {
    public  static ArrayList<HoaDonDTO> Arr_HoaDonBUS = new ArrayList<>();
    
    public static void docHoaDon() throws Exception
    {
        //trung chuyển data qua cái GUI
        HoaDonDAO data = new HoaDonDAO();
        if(Arr_HoaDonBUS == null)
            Arr_HoaDonBUS = new ArrayList<>();
        Arr_HoaDonBUS = data.docHoaDon();
        //ddd
        
    }
    public static void themHoaDon(HoaDonDTO hoadon)
    {
        HoaDonDAO data = new HoaDonDAO();
        data.them(hoadon);//gọi hàm thêm bên DAO để thêm sách vào database
        Arr_HoaDonBUS.add(hoadon);//
    }
    
    public static void suaHoaDon(HoaDonDTO hoadon)
    {
        HoaDonDAO data = new HoaDonDAO();
        data.sua(hoadon);
        Arr_HoaDonBUS.add(hoadon);//
    }
    
    public static boolean kt_trung_ma (String maHD, ArrayList<HoaDonDTO> a)
    {
        for (HoaDonDTO hoadon: a)
        {
            if(hoadon.getID_Hoadon().equals(maHD))
                return true;
        }
        return false;
    }
    public static Vector splitDAY(String day){
        Vector temp = new Vector();
        String[] parts = day.split("-");
        for(int i = 0 ; i < parts.length ; i++){
            temp.add(parts[i]);
        }
        return temp;
    }
    
    
    public static ArrayList<HoaDonDTO> check (Long ngayBDTIME ,Long ngayKTTIME){
        ArrayList<HoaDonDTO> Arr_temp = new ArrayList<>();
        Long tempNgay = null;
        Date temp;
        try {
            for(int i = 0 ; i < Arr_HoaDonBUS.size();i++){
                temp = Date.valueOf(Arr_HoaDonBUS.get(i).getNgaylap());
                tempNgay = temp.getTime();
                if( tempNgay >= ngayBDTIME && tempNgay <= ngayKTTIME){
                    Arr_temp.add(Arr_HoaDonBUS.get(i));
                }
            }
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arr_temp;
    }
    
    public static ArrayList<HoaDonDTO> findHoadon(String tuKhoa) {
            ArrayList<HoaDonDTO> Arr_temp = new ArrayList<>();
            Vector temp = new Vector();
            Vector temp1 = new Vector();
                for (int j = 0; j< Arr_HoaDonBUS.size() ;j++) {
                    temp.add(Arr_HoaDonBUS.get(j).getThanhTien());
                }
                for (int k = 0; k< Arr_HoaDonBUS.size() ;k++) {
                    temp1.add(Arr_HoaDonBUS.get(k).getTrangThai());
                }
                for (int i = 0; i< Arr_HoaDonBUS.size() ;i++) {
                    if(Arr_HoaDonBUS.get(i).getID_Hoadon().toLowerCase().contains(tuKhoa.toLowerCase())||
                    Arr_HoaDonBUS.get(i).getID_NhanVien().toLowerCase().contains(tuKhoa.toLowerCase())||
                    Arr_HoaDonBUS.get(i).getID_Khachhang().toLowerCase().contains(tuKhoa.toLowerCase())||
                    Arr_HoaDonBUS.get(i).getNgaylap().toLowerCase().contains(tuKhoa.toLowerCase())||
                    temp.get(i).toString().contains(tuKhoa.toLowerCase())||
                    temp1.get(i).toString().contains(tuKhoa.toLowerCase()))
                        Arr_temp.add(Arr_HoaDonBUS.get(i));           
                }
            return Arr_temp;
        }
  
}
