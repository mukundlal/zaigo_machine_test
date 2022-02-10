package com.example.test.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.test.R
import com.example.test.adapters.GalleryAdapter
import kotlinx.android.synthetic.main.activity_gallery.*
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class GalleryActivity : AppCompatActivity() {
    lateinit var galleryAdapter: GalleryAdapter
    val PICK_PHOTO_CODE = 101
    val REQUEST_IMAGE_CAPTURE = 102
    lateinit var currentPhotoPath: String
    lateinit var fileList: MutableList<File>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        galleryAdapter= GalleryAdapter()
        fileList=ArrayList()
        rvGallery.layoutManager=GridLayoutManager(this,3)
        rvGallery.adapter=galleryAdapter
        getFiles()
        btnTakePhoto.setOnClickListener {
            takePhoto()
        }
    }

    private fun getFiles() {
        try {
            fileList.clear()
            val path = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            fileList.addAll(path!!.listFiles())
            galleryAdapter.setData(fileList)
        }catch (e:Exception)
        {
            Log.e("Error","-----> Exception")
        }

    }



    fun takePhoto() {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            val file = createImageFile()
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.example.android.fileprovider",
                file
            )
            takePictureIntent.resolveActivity(packageManager)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this,"No Apps found",Toast.LENGTH_SHORT).show()
            // display error state to the user
        }catch (e:IOException)
        {
            Toast.makeText(this,"File creation failed",Toast.LENGTH_SHORT).show()
        }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                getFiles()
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }



}