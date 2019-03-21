-- MySQL dump 10.13  Distrib 5.6.32, for Linux (x86_64)
--
-- Host: localhost    Database: iremote
-- ------------------------------------------------------
-- Server version	5.6.32


LOCK TABLES `messagetemplate` WRITE;
/*!40000 ALTER TABLE `messagetemplate` DISABLE KEYS */;
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','doorbellring','Có khách đang ${time}bấm chuông cửa${name}của bạn, vui lòng xác nhận thân phận của khách, chú ý sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','doorlockopen','Khoá cửa của bạn${name}, đã được mở, vui lòng chú ý đến sự an toàn cho gia đình của bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','passworderror5times','Khoá cửa${name},5 lần nhập sai mật khẩu',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','lockkeyerror','Khoá cửa của bạn${name},phát hiện khoá được cắm không khớp，vui lòng chú ý sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','lockopeninside','Your doorlock ${name} has been unlocked from inside, please pay attention to home safety.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','lockkeyevent','Khoá cửa của bạn${name},phát hiện có người cố tình dùng chìa khoá để mở cửa, vui lòng chú ý đến sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','locklockerror','Khoá cửa của bạn${name},không thể đóng một cách bình thường, vui lòng chú ý đến sự an toàn cho gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','dooropen','Thiết bị của bạn${name},phát hiện khoá đã được mở, vui lòng chú ý đến sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','gasleak','Thiết bị của bạn${name},phát hiện khí gas bị rò rỉ, vui lòng chú ý đến sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','gooutwarning','Thiết bị của bạn${name},phát hiện cửa chưa được đóng, vui lòng chú ý đến sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','invation','Bạn của bạn${phonenumber},mời bạn dùng thử hệ thống thông minh Meiju(quản gia Tiểu Bạch)，địa chỉ để tải về：http://iremote.isurpass.com.cn/iremote/download.html',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','iremoteownerchanged','Của bạn${name}đã bị người khác thiết lập lại, vui lòng chú ý sự an toàn cho gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','lowbattery','Thiết bị của bạn${name},sắp hết pin, vui lòng thay pin.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','malfunction','Thiết bị của bạn${name},có thể đã sảy ra sự cố, vui lòng kiểm tra.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','move','Thiết bị của bạn${name},phát hiện có vật thể di chuyển,vui lòng chú ý đến sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','cameradectectmove','Camera quay phim của bạn${name},phát hiện có vật thể di chuyển, vui lòng chú ý đến sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','movein','Thiết bị của bạn${name},phát hiện có người vào nhà, vui lòng chú ý đến sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','randcode','Mã xác minh của bạn là ${randcode}, vui lòng nhập mã xác minh trong vòng 120 giây ，không tiết lộ mã xác minh cho người khác.',null , null);

insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','remoteoffline','Của bạn${name}đã ngoại tuyến, vui lòng kiểm tra có sự cố  nào không.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','cameraoffline','Của bạn${name}đã ngoại tuyến vui lòng kiểm tra có sự cố nào không.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','sharerequest','Bạn của bạn${phonenumber},đã uỷ quyền hệ thống thông minh cho bạn." ',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','smoke','Thiết bị của bạn${name},phát hiện có khói bất thường, vui lòng chú ý đến sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','smsrunout','Trong tháng này đã gửi cho bạn 5 tin nhắn (không bao gồm tin nhắn này), đã đạt đến giới hạn tin nhắn của tháng, nếu trong tháng này có cảnh báo, sẽkhông gửi tin nhắn cho bạn nữa, vui lòng chú ý đến sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','tampleralarm','Thiết bị của bạn${name},đang bị gỡ bỏ, vui lòng chú ý đến sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','sos','Thiết bị của bạn${name},phát ra lời cảnh báo SOS, vui lòng đảm xác nhận sự an toàn của các thành viên trong gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','waterleak','Thiết bị của bạn${name},phát hiện nước bị rò rỉ, vui lòng chú ý đến sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','templockuser','Người dùng tạm thời',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','lockuser','Người dùng',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','lockadmin','Quản trị viên',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','doorlockopenshort','Khoá cửa${name},đã được mở',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','dooropenshort','Cửa từ${name},đã được mở',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','gasleakshort','${name},rò rỉ khí gas',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','moveinshort','${name},có người vào nhà',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','smokeshort','${name},khói',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','tampleralarmshort','${name},bị gỡ bỏ',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','waterleakshort','${name},rò rỉ nước',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','passworderror5timesshort','Khoá cửa${name},5 lần nhập sai mật khẩu',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','lockkeyerrorshort','Khoá cửa ${name},chìa khoá không khớp',null , null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('channel', 'vi_VN', '0', 'Nút bấm ', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('channel', 'vi_VN', '0', 'Nút bấm ', null, null);

insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','sosshort','${name},báo động SOS',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','poweroverloadshort','${name},power overload ',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','keylockuser','Người dùng khoá',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','cardlockuser','Người dùng thẻ từ',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','passwordlockuser','Người dùng mật khẩu',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','fingerprintslockuser','Người dùng vân tay',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','facelockuser','Người dùng nhận diện khuôn mặt',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','lockautoclose','Auto lock',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','remotebatterypowerd','Hệ thống chức năng của bạn${name}, đã chuyển sang chế độ cấp điện khẩn cấp, lượng pin còn lại ${battery}, vui lòng chú ý đến sự an toàn cho gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','bulliedopenlock','Người nhà của bạn${username},thông qua khoá${name}, phát ra lời cảnh báo bị uy hiếp, vui lòng chú ý sự an toàn của gia đình bạn.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','sendtemppassword','Bạn của bạn${phonenumber}, gửi cho bạn mật khẩu tạm thời${password}, bạn có thể dùng mật khẩu này để mở khoá, thời gian hợp lệ của mật khẩu là 24 giờ.',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','deviceauthrize','Bạn nhận được uỷ quyền，từ${validfrom}đến${validthrough}，bạn có thể sử dụng kết nối${url}  ,mở${name}khoá cửa，mã xác minh của khoá cửa là${code}',null , null);
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','poweroverload','${name} were over loaded, Please Caution.',null , null);
					
insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','armfailed','Thiết lập chống trộm thất bại, vui lòng kiểm tra thiết bị liệu đã sẵn sàng, vui lòng chú ý đến sự an toàn của gia đình bạn',null , null);

insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','dscalarm','Thiết bị của bạn${name}, phát cảnh báo, vui lòng chú ý đến sự an toàn của gia đình bạn',null , null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('channel', 'vi_VN', '0', 'Nút bấm', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_1', 'vi_VN', '0', 'Cảm biến khói', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_2', 'vi_VN', '0', 'Rò rỉ nước', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_3', 'vi_VN', '0', 'Khí gas', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_4', 'vi_VN', '0', 'Cửa từ', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_5', 'vi_VN', '0', 'Khoá cửa', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_6', 'vi_VN', '0', 'Tia hồng ngoại', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_7', 'vi_VN', '0', 'Công tắc', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_8', 'vi_VN', '0', 'Công tắc', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_9', 'vi_VN', '0', 'Công tắc', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_10', 'vi_VN', '0', 'Máy báo động', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_11', 'vi_VN', '0', 'Ổ cắm', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_12', 'vi_VN', '0', 'Cánh tay robot', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_13', 'vi_VN', '0', 'Rèm cửa', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_14', 'vi_VN', '0', 'Điều hoà', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_15', 'vi_VN', '0', 'Đồng hồ đo điện', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_16', 'vi_VN', '0', 'SOS', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_17', 'vi_VN', '0', 'Đồng hồ đo lượng nước', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_18', 'vi_VN', '0', 'Mắt mèo', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_19', 'vi_VN', '0', 'Khoá cửa', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_20', 'vi_VN', '0', 'Đèn điều chỉnh ánh sáng', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_22', 'vi_VN', '0', 'Cửa từ', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_23', 'vi_VN', '0', 'Hộp không khí', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_24', 'vi_VN', '0', 'Bảng điều khiển ngữ cảnh', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_25', 'vi_VN', '0', 'Bảng điều khiển ngữ cảnh', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_26', 'vi_VN', '0', 'Bảng điều khiển ngữ cảnh', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_27', 'vi_VN', '0', 'Bảng điều khiển ngữ cảnh', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_28', 'vi_VN', '0', 'Máy điều hoà (máy tản nhiệt)', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_29', 'vi_VN', '0', 'Gió tươi mới', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_30', 'vi_VN', '0', 'Rạp chiếu phim gia đình', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_31', 'vi_VN', '0', 'Công tắc không dây', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_32', 'vi_VN', '0', 'Công tắc không dây', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_33', 'vi_VN', '0', 'Công tắc không dây', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_34', 'vi_VN', '0', 'Công tắc không dây', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_35', 'vi_VN', '0', 'Công tắc không dây', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_36', 'vi_VN', '0', 'Máy hút ẩm', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_37', 'vi_VN', '0', 'Máy hút ẩm', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_38', 'vi_VN', '0', 'Gió tươi mới', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_39', 'vi_VN', '0', 'Nhạc nền', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_40', 'vi_VN', '0', 'Gió tươi mới', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_41', 'vi_VN', '0', 'Bảng điều khiển  ngữ cảnh công tắc', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_42', 'vi_VN', '0', 'Bảng điều khiển  ngữ cảnh công tắc', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_43', 'vi_VN', '0', 'Bảng điều khiển  ngữ cảnh công tắc', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_44', 'vi_VN', '0', 'Máy chiếu', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_45', 'vi_VN', '0', 'Thiết lập máy chống trộm', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_46', 'vi_VN', '0', 'Đèn điều chỉnh ánh sáng', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_47', 'vi_VN', '0', 'DSC', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_48', 'vi_VN', '0', 'Bảng điều khiển ngữ cảnh ', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_49', 'vi_VN', '0', 'Bảng điều khiển ngữ cảnh ', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_50', 'vi_VN', '0', 'Bảng điều khiển ngữ cảnh ', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_51', 'vi_VN', '0', 'Bộ điều khiển máy sưởi', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('defaultname_52', 'vi_VN', '0', 'Bảng điều khiển ngữ cảnh ', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('partition', 'vi_VN', '9', 'Zone ', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('dscchannel', 'vi_VN', '9', 'Canal ', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('partition', 'vi_VN', '9', 'Khu chống trộm ', null, null);
insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('dscchannel', 'vi_VN', '9', 'Kênh ', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailregist_subject', 'vi_VN', '0', 'Đăng ký email', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailregist_content', 'vi_VN', '0', '<a href=\'${url}/iremote/mailuser/enableduser?${value}\'>click vào đây </a>, kích hoạt tài khoản, có hiệu lực trong 24h, nếu không phải xác minh lại, hãy nhanh chóng kích hoạt', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailresetpassword_subject', 'vi_VN', '0', 'Tìm lại mật khẩu', null, null);

insert into `messagetemplate` (`key`, `language`, `platform`, `value`, `alitemplatecode`, `alitemplateparam`) values ('mailresetpassword_content', 'vi_VN', '0', '<a href=\'${url}/iremote/mailuser/resetpasswordpage?${value}\'>click vào đây</a> tìm lại mật khẩu, có hiệu lực trong 24h, nếu không sẽ phải xác minh lại!', null, null);

insert into `messagetemplate` (`platform`, `language`, `key`, `value`,`alitemplatecode`,`alitemplateparam`) values('0','vi_VN','armfailed2','Thiết lập chống trộm thất bại, hãy kiểm tra xem thiết bị đã đặt đúng vị trí chưa, chú ý an toàn',null , null);



















/*!40000 ALTER TABLE `messagetemplate` ENABLE KEYS */;
UNLOCK TABLES;

