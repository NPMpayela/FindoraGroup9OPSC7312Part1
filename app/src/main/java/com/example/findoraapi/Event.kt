package com.example.findoraapi

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcel
import android.os.Parcelable
import java.io.ByteArrayOutputStream

data class Event(

    val title: String,
    val organisers: String,
    val location: String,
    val category: String,
    val date: String, // Ensure this is in the correct format (ISO 8601)
    val details: String)

//    var title: String = "",
//    var organisers: String = "",
//    var category: String = "",
//    var date: String = "",
//    val location: String = "",
//    var details: String = "",

//    var eventType: String = "",
//    var startTime: String = "",
//    var endTime: String = "",
//    var imageBitmap: Bitmap? = null
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        title = parcel.readString() ?: "",
//        organisers = parcel.readString() ?: "",
//        category = parcel.readString() ?: "",
//        //eventType = parcel.readString() ?: "",
//        location = parcel.readString() ?: "",
//        date = parcel.readString() ?: "",
//        details = parcel.readString() ?: "",
////        startTime = parcel.readString() ?: "",
////        endTime = parcel.readString() ?: "",
////        imageBitmap = parcel.readParcelableBitmap() // Deserialize Bitmap
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(title)
//        parcel.writeString(organisers)
//        parcel.writeString(category)
//        parcel.writeString(eventType)
//        parcel.writeString(date)
//        parcel.writeString(location)
//        parcel.writeString(details)
//        parcel.writeString(startTime)
//        parcel.writeString(endTime)
//        parcel.writeParcelableBitmap(imageBitmap, flags) // Serialize Bitmap
//         }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Event> {
//        override fun createFromParcel(parcel: Parcel): Event {
//            return Event(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Event?> {
//            return arrayOfNulls(size)
//        }
//    }
//}
//
//        // Extension function to write Bitmap as ByteArray
//        fun Parcel.writeParcelableBitmap(bitmap: Bitmap?, flags: Int) {
//            if (bitmap != null) {
//                val stream = ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                val byteArray = stream.toByteArray()
//                writeByteArray(byteArray)
//            } else {
//                writeByteArray(null)
//            }
//        }
//
//        // Extension function to read Bitmap from ByteArray
//        fun Parcel.readParcelableBitmap(): Bitmap? {
//            val byteArray = createByteArray()
//            return if (byteArray != null && byteArray.isNotEmpty()) {
//                BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//            } else {
//                null
//            }
//        }
//
