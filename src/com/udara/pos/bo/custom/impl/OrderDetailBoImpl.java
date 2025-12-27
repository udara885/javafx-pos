package com.udara.pos.bo.custom.impl;

import com.udara.pos.bo.custom.OrderDetailBo;
import com.udara.pos.dao.DaoFactory;
import com.udara.pos.dao.custom.ItemDetailDao;
import com.udara.pos.dao.custom.OrderDetailDao;
import com.udara.pos.dao.custom.ProductDetailDao;
import com.udara.pos.db.DbConnection;
import com.udara.pos.dto.ItemDetailDto;
import com.udara.pos.dto.OrderDetailDto;
import com.udara.pos.entitiy.ItemDetail;
import com.udara.pos.entitiy.OrderDetail;
import com.udara.pos.enums.DaoType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailBoImpl implements OrderDetailBo {
    OrderDetailDao dao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    ItemDetailDao detailDao = DaoFactory.getInstance().getDao(DaoType.ITEM_DETAIL);
    ProductDetailDao productDetailDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT_DETAIL);

    @Override
    public boolean makeOrder(OrderDetailDto dto) throws SQLException {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            if (saveOrder(dto)) {
                boolean isItemsSaved = saveItemDetails(dto.getItemDetailsDto(), dto.getCode());
                if (isItemsSaved) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    @Override
    public int getLastId() throws SQLException, ClassNotFoundException {
        return dao.getLastId();
    }

    private boolean saveOrder(OrderDetailDto dto) throws SQLException, ClassNotFoundException {
        return dao.save(new OrderDetail(dto.getCode(), dto.getIssuedDate(), dto.getTotalCost(), dto.getCustomerEmail(), dto.getDiscount(), dto.getUserEmail()));
    }

    private boolean saveItemDetails(List<ItemDetailDto> list, int orderCode) throws SQLException, ClassNotFoundException {
        for (ItemDetailDto dto : list) {
            boolean isItemSaved = detailDao.save(new ItemDetail(dto.getDetailCode(), orderCode, dto.getQty(), dto.getDiscount(), dto.getAmount()));
            if (isItemSaved) {
                if (!updateQty(dto.getDetailCode(), dto.getQty())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String productCode, int qty) throws SQLException, ClassNotFoundException {
        return productDetailDao.manageQty(productCode, qty);
    }
}
