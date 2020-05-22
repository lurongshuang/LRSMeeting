package com.lrs.lrsmeeting.bean;

import java.util.List;

/**
 * @description 作用:
 * @date: 2020/5/22
 * @author: 卢融霜
 */
public class MeetingDel {

    /**
     * result : 0
     * memberList : []
     * method : getMeetingById
     * topics : [{"id":1,"title":"议题1","content":"","description":null,"appointmentId":16,"orgId":null,"isSolved":false,"updateTime":1590049492000,"insertTime":null,"deleted":false},{"id":2,"title":"议题2","content":"","description":null,"appointmentId":16,"orgId":null,"isSolved":false,"updateTime":1590049492000,"insertTime":null,"deleted":false}]
     * module : meeting
     * members : [{"codeId":"admin","role":1,"pictureurl":"./image/image_486726885732342775.jpg","name":"Admin","id":2,"email":"282252158@qq.com","memberId":18},{"codeId":"h1","role":2,"pictureurl":"./img/big_profile_pic.png","name":"h1","id":3,"email":"h1@162.com","memberId":19},{"codeId":"h2","role":2,"pictureurl":"./img/big_profile_pic.png","name":"h2","id":4,"email":"h2@163.com","memberId":20}]
     * meetingId : 16
     * polls : []
     * rolls : []
     * meeting : {"template":1,"postUrl":"./img/metingPoster.jpg","isPassword":false,"isRecord":false,"description":"撒旦发射点发啊啊是发顺丰阿斯蒂芬阿斯顿阿三","maxNumberParticipants":0,"type":2,"room":{"sipAccountName":null,"splitScreenStatus":null,"isMute":false,"city":null,"county":null,"ownerId":2,"whiteBoardStatus":null,"roomname":"后天的会议","sipServerAddress":null,"shareScreenId":null,"focusMode":null,"province":null,"isLocked":false,"numberLimit":200,"isPublic":false,"pictureuri":null,"summaryId":2,"definition":"720p","id":15,"isAppointment":true,"roomType":2,"appointmentName":"后天的会议","sipServerPort":null,"createtime":"20200521082403","controllerId":2,"sipPassword":null,"sipServerName":null,"sipAccount":null,"superModeId":2,"appointmentId":16,"comment":null,"isHasPwd":false,"hash":"60301462c67c50b5c28207b298e4a8af"},"duration":0,"isSetRoom":false,"inviteCode":1932,"name":"后天的会议","isPublic":false,"startTime":"20200522160000","from":{"depNo":null,"address":"","level":3,"pictureurl":"./image/image_486726885732342775.jpg","depAddress":null,"departmentId":1,"depName":"开发部","roomId":null,"isVip":null,"offer":"","phone":"15335142586","isVice":null,"name":"Admin","company":"test","id":2,"job":null,"interests":"","email":"282252158@qq.com","account":"admin","hash":"6849a4560c40c42e05b972561a400ff0"},"id":16,"endTime":"20200523040000"}
     * SID : 4fb086edfe2c28b53e18f8ec9a90d340
     */

    private int result;
    private String method;
    private String topics;
    private String module;
    private int meetingId;
    private MeetingBean meeting;
    private String SID;
    private List<?> memberList;
    private List<MembersBean> members;
    private List<?> polls;
    private List<?> rolls;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public MeetingBean getMeeting() {
        return meeting;
    }

    public void setMeeting(MeetingBean meeting) {
        this.meeting = meeting;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public List<?> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<?> memberList) {
        this.memberList = memberList;
    }

    public List<MembersBean> getMembers() {
        return members;
    }

    public void setMembers(List<MembersBean> members) {
        this.members = members;
    }

    public List<?> getPolls() {
        return polls;
    }

    public void setPolls(List<?> polls) {
        this.polls = polls;
    }

    public List<?> getRolls() {
        return rolls;
    }

    public void setRolls(List<?> rolls) {
        this.rolls = rolls;
    }

    public static class MeetingBean {
        /**
         * template : 1
         * postUrl : ./img/metingPoster.jpg
         * isPassword : false
         * isRecord : false
         * description : 撒旦发射点发啊啊是发顺丰阿斯蒂芬阿斯顿阿三
         * maxNumberParticipants : 0
         * type : 2
         * room : {"sipAccountName":null,"splitScreenStatus":null,"isMute":false,"city":null,"county":null,"ownerId":2,"whiteBoardStatus":null,"roomname":"后天的会议","sipServerAddress":null,"shareScreenId":null,"focusMode":null,"province":null,"isLocked":false,"numberLimit":200,"isPublic":false,"pictureuri":null,"summaryId":2,"definition":"720p","id":15,"isAppointment":true,"roomType":2,"appointmentName":"后天的会议","sipServerPort":null,"createtime":"20200521082403","controllerId":2,"sipPassword":null,"sipServerName":null,"sipAccount":null,"superModeId":2,"appointmentId":16,"comment":null,"isHasPwd":false,"hash":"60301462c67c50b5c28207b298e4a8af"}
         * duration : 0
         * isSetRoom : false
         * inviteCode : 1932
         * name : 后天的会议
         * isPublic : false
         * startTime : 20200522160000
         * from : {"depNo":null,"address":"","level":3,"pictureurl":"./image/image_486726885732342775.jpg","depAddress":null,"departmentId":1,"depName":"开发部","roomId":null,"isVip":null,"offer":"","phone":"15335142586","isVice":null,"name":"Admin","company":"test","id":2,"job":null,"interests":"","email":"282252158@qq.com","account":"admin","hash":"6849a4560c40c42e05b972561a400ff0"}
         * id : 16
         * endTime : 20200523040000
         */

        private int template;
        private String postUrl;
        private boolean isPassword;
        private boolean isRecord;
        private String description;
        private int maxNumberParticipants;
        private int type;
        private RoomBean room;
        private int duration;
        private boolean isSetRoom;
        private int inviteCode;
        private String name;
        private boolean isPublic;
        private String startTime;
        private FromBean from;
        private int id;
        private String endTime;

        public int getTemplate() {
            return template;
        }

        public void setTemplate(int template) {
            this.template = template;
        }

        public String getPostUrl() {
            return postUrl;
        }

        public void setPostUrl(String postUrl) {
            this.postUrl = postUrl;
        }

        public boolean isIsPassword() {
            return isPassword;
        }

        public void setIsPassword(boolean isPassword) {
            this.isPassword = isPassword;
        }

        public boolean isIsRecord() {
            return isRecord;
        }

        public void setIsRecord(boolean isRecord) {
            this.isRecord = isRecord;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getMaxNumberParticipants() {
            return maxNumberParticipants;
        }

        public void setMaxNumberParticipants(int maxNumberParticipants) {
            this.maxNumberParticipants = maxNumberParticipants;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public RoomBean getRoom() {
            return room;
        }

        public void setRoom(RoomBean room) {
            this.room = room;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public boolean isIsSetRoom() {
            return isSetRoom;
        }

        public void setIsSetRoom(boolean isSetRoom) {
            this.isSetRoom = isSetRoom;
        }

        public int getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(int inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isIsPublic() {
            return isPublic;
        }

        public void setIsPublic(boolean isPublic) {
            this.isPublic = isPublic;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public FromBean getFrom() {
            return from;
        }

        public void setFrom(FromBean from) {
            this.from = from;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public static class RoomBean {
            /**
             * sipAccountName : null
             * splitScreenStatus : null
             * isMute : false
             * city : null
             * county : null
             * ownerId : 2
             * whiteBoardStatus : null
             * roomname : 后天的会议
             * sipServerAddress : null
             * shareScreenId : null
             * focusMode : null
             * province : null
             * isLocked : false
             * numberLimit : 200
             * isPublic : false
             * pictureuri : null
             * summaryId : 2
             * definition : 720p
             * id : 15
             * isAppointment : true
             * roomType : 2
             * appointmentName : 后天的会议
             * sipServerPort : null
             * createtime : 20200521082403
             * controllerId : 2
             * sipPassword : null
             * sipServerName : null
             * sipAccount : null
             * superModeId : 2
             * appointmentId : 16
             * comment : null
             * isHasPwd : false
             * hash : 60301462c67c50b5c28207b298e4a8af
             */

            private Object sipAccountName;
            private Object splitScreenStatus;
            private boolean isMute;
            private Object city;
            private Object county;
            private int ownerId;
            private Object whiteBoardStatus;
            private String roomname;
            private Object sipServerAddress;
            private Object shareScreenId;
            private Object focusMode;
            private Object province;
            private boolean isLocked;
            private int numberLimit;
            private boolean isPublic;
            private Object pictureuri;
            private int summaryId;
            private String definition;
            private int id;
            private boolean isAppointment;
            private int roomType;
            private String appointmentName;
            private Object sipServerPort;
            private String createtime;
            private int controllerId;
            private Object sipPassword;
            private Object sipServerName;
            private Object sipAccount;
            private int superModeId;
            private int appointmentId;
            private Object comment;
            private boolean isHasPwd;
            private String hash;

            public Object getSipAccountName() {
                return sipAccountName;
            }

            public void setSipAccountName(Object sipAccountName) {
                this.sipAccountName = sipAccountName;
            }

            public Object getSplitScreenStatus() {
                return splitScreenStatus;
            }

            public void setSplitScreenStatus(Object splitScreenStatus) {
                this.splitScreenStatus = splitScreenStatus;
            }

            public boolean isIsMute() {
                return isMute;
            }

            public void setIsMute(boolean isMute) {
                this.isMute = isMute;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public Object getCounty() {
                return county;
            }

            public void setCounty(Object county) {
                this.county = county;
            }

            public int getOwnerId() {
                return ownerId;
            }

            public void setOwnerId(int ownerId) {
                this.ownerId = ownerId;
            }

            public Object getWhiteBoardStatus() {
                return whiteBoardStatus;
            }

            public void setWhiteBoardStatus(Object whiteBoardStatus) {
                this.whiteBoardStatus = whiteBoardStatus;
            }

            public String getRoomname() {
                return roomname;
            }

            public void setRoomname(String roomname) {
                this.roomname = roomname;
            }

            public Object getSipServerAddress() {
                return sipServerAddress;
            }

            public void setSipServerAddress(Object sipServerAddress) {
                this.sipServerAddress = sipServerAddress;
            }

            public Object getShareScreenId() {
                return shareScreenId;
            }

            public void setShareScreenId(Object shareScreenId) {
                this.shareScreenId = shareScreenId;
            }

            public Object getFocusMode() {
                return focusMode;
            }

            public void setFocusMode(Object focusMode) {
                this.focusMode = focusMode;
            }

            public Object getProvince() {
                return province;
            }

            public void setProvince(Object province) {
                this.province = province;
            }

            public boolean isIsLocked() {
                return isLocked;
            }

            public void setIsLocked(boolean isLocked) {
                this.isLocked = isLocked;
            }

            public int getNumberLimit() {
                return numberLimit;
            }

            public void setNumberLimit(int numberLimit) {
                this.numberLimit = numberLimit;
            }

            public boolean isIsPublic() {
                return isPublic;
            }

            public void setIsPublic(boolean isPublic) {
                this.isPublic = isPublic;
            }

            public Object getPictureuri() {
                return pictureuri;
            }

            public void setPictureuri(Object pictureuri) {
                this.pictureuri = pictureuri;
            }

            public int getSummaryId() {
                return summaryId;
            }

            public void setSummaryId(int summaryId) {
                this.summaryId = summaryId;
            }

            public String getDefinition() {
                return definition;
            }

            public void setDefinition(String definition) {
                this.definition = definition;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isIsAppointment() {
                return isAppointment;
            }

            public void setIsAppointment(boolean isAppointment) {
                this.isAppointment = isAppointment;
            }

            public int getRoomType() {
                return roomType;
            }

            public void setRoomType(int roomType) {
                this.roomType = roomType;
            }

            public String getAppointmentName() {
                return appointmentName;
            }

            public void setAppointmentName(String appointmentName) {
                this.appointmentName = appointmentName;
            }

            public Object getSipServerPort() {
                return sipServerPort;
            }

            public void setSipServerPort(Object sipServerPort) {
                this.sipServerPort = sipServerPort;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public int getControllerId() {
                return controllerId;
            }

            public void setControllerId(int controllerId) {
                this.controllerId = controllerId;
            }

            public Object getSipPassword() {
                return sipPassword;
            }

            public void setSipPassword(Object sipPassword) {
                this.sipPassword = sipPassword;
            }

            public Object getSipServerName() {
                return sipServerName;
            }

            public void setSipServerName(Object sipServerName) {
                this.sipServerName = sipServerName;
            }

            public Object getSipAccount() {
                return sipAccount;
            }

            public void setSipAccount(Object sipAccount) {
                this.sipAccount = sipAccount;
            }

            public int getSuperModeId() {
                return superModeId;
            }

            public void setSuperModeId(int superModeId) {
                this.superModeId = superModeId;
            }

            public int getAppointmentId() {
                return appointmentId;
            }

            public void setAppointmentId(int appointmentId) {
                this.appointmentId = appointmentId;
            }

            public Object getComment() {
                return comment;
            }

            public void setComment(Object comment) {
                this.comment = comment;
            }

            public boolean isIsHasPwd() {
                return isHasPwd;
            }

            public void setIsHasPwd(boolean isHasPwd) {
                this.isHasPwd = isHasPwd;
            }

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }
        }

        public static class FromBean {
            /**
             * depNo : null
             * address :
             * level : 3
             * pictureurl : ./image/image_486726885732342775.jpg
             * depAddress : null
             * departmentId : 1
             * depName : 开发部
             * roomId : null
             * isVip : null
             * offer :
             * phone : 15335142586
             * isVice : null
             * name : Admin
             * company : test
             * id : 2
             * job : null
             * interests :
             * email : 282252158@qq.com
             * account : admin
             * hash : 6849a4560c40c42e05b972561a400ff0
             */

            private Object depNo;
            private String address;
            private int level;
            private String pictureurl;
            private Object depAddress;
            private int departmentId;
            private String depName;
            private Object roomId;
            private Object isVip;
            private String offer;
            private String phone;
            private Object isVice;
            private String name;
            private String company;
            private int id;
            private Object job;
            private String interests;
            private String email;
            private String account;
            private String hash;

            public Object getDepNo() {
                return depNo;
            }

            public void setDepNo(Object depNo) {
                this.depNo = depNo;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getPictureurl() {
                return pictureurl;
            }

            public void setPictureurl(String pictureurl) {
                this.pictureurl = pictureurl;
            }

            public Object getDepAddress() {
                return depAddress;
            }

            public void setDepAddress(Object depAddress) {
                this.depAddress = depAddress;
            }

            public int getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(int departmentId) {
                this.departmentId = departmentId;
            }

            public String getDepName() {
                return depName;
            }

            public void setDepName(String depName) {
                this.depName = depName;
            }

            public Object getRoomId() {
                return roomId;
            }

            public void setRoomId(Object roomId) {
                this.roomId = roomId;
            }

            public Object getIsVip() {
                return isVip;
            }

            public void setIsVip(Object isVip) {
                this.isVip = isVip;
            }

            public String getOffer() {
                return offer;
            }

            public void setOffer(String offer) {
                this.offer = offer;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getIsVice() {
                return isVice;
            }

            public void setIsVice(Object isVice) {
                this.isVice = isVice;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getJob() {
                return job;
            }

            public void setJob(Object job) {
                this.job = job;
            }

            public String getInterests() {
                return interests;
            }

            public void setInterests(String interests) {
                this.interests = interests;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }
        }
    }

    public static class MembersBean {
        /**
         * codeId : admin
         * role : 1
         * pictureurl : ./image/image_486726885732342775.jpg
         * name : Admin
         * id : 2
         * email : 282252158@qq.com
         * memberId : 18
         */

        private String codeId;
        private int role;
        private String pictureurl;
        private String name;
        private int id;
        private String email;
        private int memberId;

        public String getCodeId() {
            return codeId;
        }

        public void setCodeId(String codeId) {
            this.codeId = codeId;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getPictureurl() {
            return pictureurl;
        }

        public void setPictureurl(String pictureurl) {
            this.pictureurl = pictureurl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }
    }
}
