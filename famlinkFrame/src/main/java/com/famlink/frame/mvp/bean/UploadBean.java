package com.famlink.frame.mvp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangkai on 16/6/23.
 */
public class UploadBean extends BaseResult implements Serializable {

    public UserInfoEntity data;

    public UserInfoEntity getData() {
        return data;
    }

    public void setData(UserInfoEntity data) {
        this.data = data;
    }

    public class UserInfoEntity implements Serializable{
        /*
     * zlw
     * 用户信息实体类
     * **/
        private String headIcon;//头像地址
        private String nick;//昵称
        private String userId;//用户名
        private String sig;//腾讯互动视频分配的签名。
        private String expires;
        private String token;//用户登陆后服务端自动生成的一个唯一标识，以后每次的url传递中都需要带有此参数
        private String phoneNumber;
        private String sex;
        private String apValue;//评分
        private String balance;//余额
        private String fanNum;//粉丝数
        private String dealNum; //成交数
        private String userType;  //用户类型：1：普通用户，2：第三方微信注册
        private String status;

        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }

        public String getDealNum() {
            return dealNum;
        }
        public void setDealNum(String dealNum) {
            this.dealNum = dealNum;
        }

        private List<LivingBean> roomList;

        public List<LivingBean> getRoomList() {
            return roomList;
        }
        public void setRoomList(List<LivingBean> roomList) {
            this.roomList = roomList;
        }
        public String getSex() {
            return sex;
        }
        public String getApValue() {
            return apValue;
        }
        public void setApValue(String apValue) {
            this.apValue = apValue;
        }
        public String getBalance() {
            return balance;
        }
        public void setBalance(String balance) {
            this.balance = balance;
        }
        public String getFanNum() {
            return fanNum;
        }
        public void setFanNum(String fanNum) {
            this.fanNum = fanNum;
        }
        public void setSex(String sex) {
            this.sex = sex;
        }
        public String getToken() {
            return token;
        }
        public void setToken(String token) {
            this.token = token;
        }
        private String email;


        public String getPhoneNumber() {
            return phoneNumber;
        }
        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getNick() {
            return nick;
        }
        public void setNick(String nick) {
            this.nick = nick;
        }
        public String getUserId() {
            return userId;
        }
        public void setUserId(String userId) {
            this.userId = userId;
        }
        public String getSig() {
            return sig;
        }
        public void setSig(String sig) {
            this.sig = sig;
        }
        public String getHeadIcon() {
            return headIcon;
        }
        public void setHeadIcon(String headIcon) {
            this.headIcon = headIcon;
        }
        public String getExpires() {
            return expires;
        }
        public void setExpires(String expires) {
            this.expires = expires;
        }
        public String getUserType() {
            return userType;
        }
        public void setUserType(String userType) {
            this.userType = userType;
        }




        public class LivingBean implements Serializable{
            /**
             *
             */
            private static final long serialVersionUID = 1L;
            private int id;//房间自增序列号，作为热门列表分页查询的一个条件：移动端需要分页的时候需要传递这个值作为pageNo传递给后台
            private int watchNum;//观看数 -----作为关注列表的一个分页查询条件，当且仅当返回的最后一条数据为直播的时候，分页传递的pageNo为watchNum
            private int previewNum;//预约数-----作为关注列表的一个分页查询条件，当且晋档返回的最后一条数据类型为预告的时候，分页传递的pageNo为priviceNum
            private String sellerId;//卖家userId
            private String sellerNick;//卖家昵称
            private String sellerHeadIcon;//卖家头像地址
            private String recommenValue;//卖家推荐值
            private String roomNo;//对应表中的roomId
            private String roomTitle;//房间标题
            private String roomLogo;//房间背景图片
            private int roomType;//房间类型 1：直播 2:预告 3：录播
            private String language;//创建房间时候选择的语言类型，0：汉语 1：英语
            private String createTime;//房间创建时间
            private String startTime;//房间开始直播时间
            private String endTime;//针对已经直播过的视频会有此时间
            private String location;//定位地址
            private String longitude;//经度
            private String latitude;//纬度
            private int tradNum;//交易总数(卖家的交易总量)
            private int curTradNum;//当前交易数(卖家当前直播间的交易总量)
            private int browsNum;//浏览数
            private int fansNum;//粉丝数
            private String praiseValue="3.7";//好评值
            private String deliveryValue = "3.3";//发货值
            private String groupId;  //组ID
            private String isFocus;//当前用户跟房间创建者是否是关注状况0：没关注  1：已关注
            private int isRemind;//当前用户跟房间是否预约提醒过，0表示未设置提醒，1表示设置过提醒
            private List<LivingProductBean> detailList;

            public String getSellerId() {
                return sellerId;
            }
            public void setSellerId(String sellerId) {
                this.sellerId = sellerId;
            }
            public String getSellerNick() {
                return sellerNick;
            }
            public void setSellerNick(String sellerNick) {
                this.sellerNick = sellerNick;
            }
            public String getRoomTitle() {
                return roomTitle;
            }
            public void setRoomTitle(String roomTitle) {
                this.roomTitle = roomTitle;
            }
            public String getRoomLogo() {
                return roomLogo;
            }
            public void setRoomLogo(String roomLogo) {
                this.roomLogo = roomLogo;
            }
            public int getWatchNum() {
                return watchNum;
            }
            public void setWatchNum(int watchNum) {
                this.watchNum = watchNum;
            }
            public String getLocation() {
                return location;
            }
            public void setLocation(String location) {
                this.location = location;
            }
            public String getLongitude() {
                return longitude;
            }
            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
            public String getLatitude() {
                return latitude;
            }
            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }
            public int getTradNum() {
                return tradNum;
            }
            public void setTradNum(int tradNum) {
                this.tradNum = tradNum;
            }
            public int getPreviewNum() {
                return previewNum;
            }
            public void setPreviewNum(int previewNum) {
                this.previewNum = previewNum;
            }
            public int getFansNum() {
                return fansNum;
            }
            public void setFansNum(int fansNum) {
                this.fansNum = fansNum;
            }
            public String getPraiseValue() {
                return praiseValue;
            }
            public void setPraiseValue(String praiseValue) {
                this.praiseValue = praiseValue;
            }
            public String getDeliveryValue() {
                return deliveryValue;
            }
            public void setDeliveryValue(String deliveryValue) {
                this.deliveryValue = deliveryValue;
            }
            public String getRoomNo() {
                return roomNo;
            }
            public void setRoomNo(String roomNo) {
                this.roomNo = roomNo;
            }
            public String getSellerHeadIcon() {
                return sellerHeadIcon;
            }
            public void setSellerHeadIcon(String sellerHeadIcon) {
                this.sellerHeadIcon = sellerHeadIcon;
            }
            public int getRoomType() {
                return roomType;
            }
            public void setRoomType(int roomType) {
                this.roomType = roomType;
            }
            public String getLanguage() {
                return language;
            }
            public void setLanguage(String language) {
                this.language = language;
            }
            public int getBrowsNum() {
                return browsNum;
            }
            public void setBrowsNum(int browsNum) {
                this.browsNum = browsNum;
            }
            public int getCurTradNum() {
                return curTradNum;
            }
            public void setCurTradNum(int curTradNum) {
                this.curTradNum = curTradNum;
            }
            public String getCreateTime() {
                return createTime;
            }
            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
            public String getStartTime() {
                return startTime;
            }
            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }
            public String getEndTime() {
                return endTime;
            }
            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }
            public String getRecommenValue() {
                return recommenValue;
            }
            public void setRecommenValue(String recommenValue) {
                this.recommenValue = recommenValue;
            }
            public List<LivingProductBean> getDetailList() {
                return detailList;
            }
            public void setDetailList(List<LivingProductBean> detailList) {
                this.detailList = detailList;
            }
            public int getId() {
                return id;
            }
            public void setId(int id) {
                this.id = id;
            }
            public String getGroupId() {
                return groupId;
            }
            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }
            public String getIsFocus() {
                return isFocus;
            }
            public void setIsFocus(String isFocus) {
                this.isFocus = isFocus;
            }
            public int getIsRemind() {
                return isRemind;
            }
            public void setIsRemind(int isRemind) {
                this.isRemind = isRemind;
            }

            public class LivingProductBean implements Serializable {

                /**
                 *
                 */
                private static final long serialVersionUID = 1L;


                /**
                 * tb_product.id (ID)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String id;
                /**
                 * tb_product.product_id
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String productId;
                /**
                 * tb_product.product_cate_id (分类ID)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String productCateId;
                /**
                 * tb_product.name (商品名称)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String name;
                /**
                 * tb_product.product_brand_id (品牌ID)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String productBrandId;
                /**
                 * tb_product.market_price (市场价)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String marketPrice;
                /**
                 * tb_product.actual_sales (实际销量)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String actualSales;
                /**
                 * tb_product.product_stock (商品库存)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String productStock;
                /**
                 * tb_product.state (状态：1、正常；2、商品删除；3、上架；4、下架)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String state;
                /**
                 * tb_product.user_id (用户ID)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String userId;
                /**
                 * tb_product.create_time (创建时间)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String createTime;
                /**
                 * tb_product.master_img (主图)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String masterImg;
                /**
                 * tb_product.currency (币种：1、人民币；2、美币)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String currency;
                /**
                 * tb_product.fare_id (运费模板ID)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String fareId;
                /**
                 * tb_product.update_time (更新时间)
                 * @ibatorgenerated 2016-04-21 17:43:49
                 */
                private String updateTime;


                public String getId() {
                    return id;
                }
                public void setId(String id) {
                    this.id = id;
                }
                public String getProductId() {
                    return productId;
                }
                public void setProductId(String productId) {
                    this.productId = productId;
                }
                public String getProductCateId() {
                    return productCateId;
                }
                public void setProductCateId(String productCateId) {
                    this.productCateId = productCateId;
                }
                public String getName() {
                    return name;
                }
                public void setName(String name) {
                    this.name = name;
                }
                public String getProductBrandId() {
                    return productBrandId;
                }
                public void setProductBrandId(String productBrandId) {
                    this.productBrandId = productBrandId;
                }
                public String getMarketPrice() {
                    return marketPrice;
                }
                public void setMarketPrice(String marketPrice) {
                    this.marketPrice = marketPrice;
                }
                public String getActualSales() {
                    return actualSales;
                }
                public void setActualSales(String actualSales) {
                    this.actualSales = actualSales;
                }
                public String getProductStock() {
                    return productStock;
                }
                public void setProductStock(String productStock) {
                    this.productStock = productStock;
                }
                public String getState() {
                    return state;
                }
                public void setState(String state) {
                    this.state = state;
                }
                public String getUserId() {
                    return userId;
                }
                public void setUserId(String userId) {
                    this.userId = userId;
                }
                public String getCreateTime() {
                    return createTime;
                }
                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
                public String getMasterImg() {
                    return masterImg;
                }
                public void setMasterImg(String masterImg) {
                    this.masterImg = masterImg;
                }
                public String getCurrency() {
                    return currency;
                }
                public void setCurrency(String currency) {
                    this.currency = currency;
                }
                public String getFareId() {
                    return fareId;
                }
                public void setFareId(String fareId) {
                    this.fareId = fareId;
                }
                public String getUpdateTime() {
                    return updateTime;
                }
                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }
                public long getSerialversionuid() {
                    return serialVersionUID;
                }
            }
        }
    }


}
