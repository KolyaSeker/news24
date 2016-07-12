package com.example.kolya.news24.Realm;

import io.realm.RealmObject;


public class RealmItemNews extends RealmObject
    {

        private String realmNewsTitle;
        private String realmPubDate;
        private String realmImageNews;

        public String getRealmImageNews() {
            return realmImageNews;
        }

        public String getRealmPubDate() {
            return realmPubDate;
        }

        public String getRealmNewsTitle(){
            return realmNewsTitle;
        }

        public void setRealmImageNews(String realmImageNews) {
            this.realmImageNews = realmImageNews;
        }

        public void setRealmNewsTitle(String realmNewsTitle) {
            this.realmNewsTitle = realmNewsTitle;
        }

        public void setRealmPubDate(String realmPubDate) {

            this.realmPubDate = realmPubDate;
        }
    }

