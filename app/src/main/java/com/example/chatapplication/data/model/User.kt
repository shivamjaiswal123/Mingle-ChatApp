package com.example.chatapplication.data.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val uid: String,
    val name: String,
    val bio: String,
    var profilePicUrl: String? = ""
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()
    )

    constructor() : this("", "", "", "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(name)
        parcel.writeString(bio)
        parcel.writeString(profilePicUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
