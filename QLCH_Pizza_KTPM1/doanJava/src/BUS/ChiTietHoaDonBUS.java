/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.ChiTietHoaDonDAO;
import DTO.ChiTietHoaDonDTO;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class ChiTietHoaDonBUS {
    public  static  ArrayList<ChiTietHoaDonDTO> Arr_ChiTietHoaDon = new ArrayList();
    public static void docHoaDon() throws Exception
    {
        //trung chuyển data qua cái GUI
        ChiTietHoaDonDAO data = new ChiTietHoaDonDAO();
        if(Arr_ChiTietHoaDon == null)
            Arr_ChiTietHoaDon = new ArrayList<>();
        Arr_ChiTietHoaDon = data.docchitiethoadon();
        //ddd
        
    }
}
