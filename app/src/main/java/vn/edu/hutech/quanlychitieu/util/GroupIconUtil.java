package vn.edu.hutech.quanlychitieu.util;

import vn.edu.hutech.quanlychitieu.R;

public class GroupIconUtil {
    public static int getGroupIcon(String groupName) {
        switch (groupName) {
            case "Ăn uống":
                return R.drawable.anuong;
            case "Cafe":
            case "Tiệc":
                return R.drawable.cafe;
            case "Tiền điện":
                return R.drawable.bill;
            case "Khoản chi khác":
            case "Khoản thu khác":
                return R.drawable.khoanthukhac;
            case "Tiền nước":
                return R.drawable.tiennuoc;
            case "Thẻ điện thoại":
                return R.drawable.thedienthoai;
            case "Internet":
                return R.drawable.internet;
            case "Thuê nhà":
            case "Sửa chữa nhà cửa":
                return R.drawable.home;
            case "Xăng xe":
                return R.drawable.xangxe;
            case "Thời trang":
                return R.drawable.thoitrang;
            case "Thiết bị điện tử":
                return R.drawable.network;
            case "Bạn bè & Người yêu":
                return R.drawable.banbe_nguoiyeu;
            case "Xem phim":
                return R.drawable.xemphim;
            case "Du lịch":
                return R.drawable.dulich;
            case "Thuốc":
                return R.drawable.thuoc;
            case "Chăm sóc cá nhân":
                return R.drawable.chamsoc;
            case "Con cái":
                return R.drawable.concai;
            case "Sách":
                return R.drawable.sach;
            case "Rút tiền":
            case "Bán đồ":
                return R.drawable.ruttien;
            case "Lương":
                return R.drawable.tien;
            case "Thưởng":
                return R.drawable.thuong;
            case "Tiền lãi":
                return R.drawable.tienlai;
            case "Vay tiền":
                return R.drawable.chovay;
            default:
                return R.drawable.ic_wallet_24dp;
        }
    }
}
