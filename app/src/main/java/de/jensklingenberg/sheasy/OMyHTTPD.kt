package de.jensklingenberg.rdrd


/**
 * Created by jens on 7/2/18.
 */

/*

class MyHTTPD @Throws(IOException::class)


constructor(mainActivity: MainActivity) : NanoHTTPD(PORT),IDownload {
    override fun downloadProgress(progress: Long, fileSize: Long) {
        Log.d("THIS",progress.toString()+" "+fileSize)

    }

    override fun onDownloadComplete(localFilePath: String?) {
        Log.d("THSI","DOWNLODED"+localFilePath)
    }

    override fun onDownloadError() {
        Log.d("THSI","DOWNLODED ERROR")
    }

    var main = mainActivity
    var  filePath = ""

    companion object {
        val PORT = 8766
        val API_V1="api/v1"

    }

//               newFixedLengthResponse1.addHeader("Access-Control-Allow-Origin","*")

    private fun isServiceRunning():Boolean {
        val manager = main.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE))
        {
            Log.d("THIS",service.service.className)


        }
        return false
    }




    override fun serve(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response? {
        Log.i("TAG", "serve: " + session.uri)
        var uri = session.uri
        val htserver = DebugWebSocketServer(8765,true)
        htserver.start()
        htserver.openWebSocket(session)

        uri=uri.replaceFirst("/","",true)


        val get = ApiCommand.get(uri)
        val moshi = Moshi.Builder()
                .build()

       when (get) {

           ApiComand.api->{
               val requestV1 = uri.substringAfter(API_V1);
               when{
                   requestV1.contains("Download")->{
                       return handlePullCommand(session)
                   }

                   requestV1.contains("Apps")->{
                       return NanoHTTPD.newFixedLengthResponse("ApiCommand "+uri+" not found")
                   }

                   requestV1.contains("Intent")->{
                       when(session.method){
                           Method.POST->{
                               var map = HashMap<String, String>()
                               session.parseBody(map)


                               val jsonAdapter = Moshi.Builder()
                                       .build().adapter(IntentRequest::class.java)
                               val get1 = jsonAdapter.fromJson(map[("postData")])


                               return startIntent(get1)

                           }
                       }



                   }

                   requestV1.contains("query")->{
                       return handleQuery(session)
                   }

                   requestV1.contains("start")->{
                      return handleActivityStart(requestV1)
                   }
                   requestV1.contains("services")->{
                       return  handleService();

                   }

                   requestV1.contains("contacts")->{
                       return                        handleContacts();

                   }
                   requestV1.contains("toast")->{
                       return                        handleToast();

                   }

                   requestV1.contains("device")->{
                       return handleDevice(requestV1)
                   }

                   requestV1.contains("notification")->{
                       return handleNotification(requestV1)
                   }

                   requestV1.contains("key")->{
                       return handleKey(requestV1)
                   }

                   requestV1.contains("broadcast")->{

                       when(session.method){
                           Method.POST->{
                               var map = HashMap<String, String>()
                               session.parseBody(map)


                               val jsonAdapter = Moshi.Builder()
                                       .build().adapter(IntentRequest::class.java)
                               val get1 = jsonAdapter.fromJson(map[("postData")])

                               val nextRandomNumber = Random().nextInt(100)
                               // Display the random number in TextView
                               Log.d("tggg","Random Number : " + nextRandomNumber)

                               // Initialize a new Intent object
                               val Intent = Intent()
                               // Set an action for the Intent
                               Intent.action = get1?.action
                               // Put an integer value Intent to broadcast it
                               Intent.putExtra("RandomNumber", nextRandomNumber)

                               /*
                            public abstract void sendBroadcast (Intent Intent)
                                Broadcast the given Intent to all interested BroadcastReceivers. This call
                                is asynchronous; it returns immediately, and you will continue executing
                                while the receivers are run. No results are propagated from receivers and
                                receivers can not abort the broadcast. If you want to allow receivers to
                                propagate results or abort the broadcast, you must send an ordered
                                broadcast using sendOrderedBroadcast(Intent, String).

                            Parameters
                                Intent : The Intent to broadcast; all receivers matching this Intent
                                    will receive the broadcast.

                               // Finally, send the broadcast
                               main.sendBroadcast(Intent)




                           }
                       }







                   }




                   requestV1.contains("commands")->{
                       val commResponse= arrayListOf<CommandResponse>()
                       for (command in values()) {
                           commResponse.add(CommandResponse(command.name))
                       }


                       val listMyData = Types.newParameterizedType(List::class.java, CommandResponse::class.java)
                       val adapter = moshi.adapter<List<CommandResponse>>(listMyData)

                       val newFixedLengthResponse1 = NanoHTTPD.newFixedLengthResponse(adapter.contactsToJson(commResponse))
                       newFixedLengthResponse1.addHeader("Access-Control-Allow-Origin","*")

                       return newFixedLengthResponse1

                   }
                   requestV1.contains("Pictures")->{
                       val path = Environment.getExternalStorageDirectory().toString() + "/Pictures"

                       return NanoHTTPD.newFixedLengthResponse(setup("",path))
                   }
               }
           }

           other->{
               if(uri.substringAfter("web").isEmpty()){
                   return returnAssetFile("web/index.html");

               }else{
                   return returnAssetFile(uri);

               }
           }

       }



        return NanoHTTPD.newFixedLengthResponse("ApiCommand "+uri+" not found")


  }

    private fun handleContacts(): NanoHTTPD.Response? {
        val readContacts = ContactUtils.readContacts(main.contentResolver)

        val moshi = Moshi.Builder()
                .build()

        val listMyData = Types.newParameterizedType(List::class.java, ContactResponse::class.java)
        val adapter = moshi.adapter<List<ContactResponse>>(listMyData)
        val newFixedLengthResponse1 = NanoHTTPD.newFixedLengthResponse(adapter.contactsToJson(readContacts))
        //NotificationListener.notifi= arrayListOf()
        newFixedLengthResponse1.addHeader("Access-Control-Allow-Origin","*")

        return newFixedLengthResponse1


    }

    private fun handleNotification(requestV1: String): NanoHTTPD.Response? {




        val moshi = Moshi.Builder()
                .build()

        val listMyData = Types.newParameterizedType(List::class.java, NotificationResponse::class.java)
        val adapter = moshi.adapter<List<NotificationResponse>>(listMyData)
        val newFixedLengthResponse1 = NanoHTTPD.newFixedLengthResponse(adapter.contactsToJson(NotificationListener.notifi))
        //NotificationListener.notifi= arrayListOf()
        newFixedLengthResponse1.addHeader("Access-Control-Allow-Origin","*")

        return newFixedLengthResponse1



    }

    private fun handleKey(requestV1: String): NanoHTTPD.Response? {
        //main.dispatchKeyEvent(KeyEvent(KeyEvent.KEYCODE_MEDIA_NEXT, KeyEvent.KEYCODE_MEDIA_NEXT))
        sendMediaButton(main, KeyEvent.KEYCODE_MEDIA_FAST_FORWARD);

        return NanoHTTPD.newFixedLengthResponse("Next media")


    }

    private fun sendMediaButton(context: Context, keyCode: Int) {
        var keyEvent = KeyEvent(KeyEvent.ACTION_DOWN, keyCode)
        var Intent = Intent(Intent.ACTION_MEDIA_BUTTON)
        Intent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent)
        context.sendOrderedBroadcast(Intent, null)

        keyEvent = KeyEvent(KeyEvent.ACTION_UP, keyCode)
        Intent = Intent(Intent.ACTION_MEDIA_BUTTON)
        Intent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent)
        context.sendOrderedBroadcast(Intent, null)
    }

    private fun handleToast(): NanoHTTPD.Response? {

        val toast = Toast(main)
        toast.setText("Hallo")
        toast.show()
        return NanoHTTPD.newFixedLengthResponse("Toast")

    }

    private fun handleService(): Response? {
        val services = arrayListOf<ServiceResponse>()
        val manager = main.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE))
        {
            Log.d("THIS",service.service.className)
services.add(ServiceResponse(service.service.className))

        }
        val moshi = Moshi.Builder()
                .build()

        val listMyData = Types.newParameterizedType(List::class.java, ServiceResponse::class.java)
        val adapter = moshi.adapter<List<ServiceResponse>>(listMyData)
        val newFixedLengthResponse1 = NanoHTTPD.newFixedLengthResponse(adapter.contactsToJson(services))
        newFixedLengthResponse1.addHeader("Access-Control-Allow-Origin","*")

        return newFixedLengthResponse1

    }

    private fun handleActivityStart(requestV1: String): Response? {

        val pm = main.getPackageManager()
        val appStartIntent = pm.getLaunchIntentForPackage(requestV1.substringAfter("start/"))
        if (null != appStartIntent) {
            main.startActivity(appStartIntent)
        }

        return NanoHTTPD.newFixedLengthResponse("OccKAY")

    }

    private fun startIntent(action: IntentRequest?): Response? {
        val sendIntent = Intent()
        sendIntent.action = action?.action //android.Intent.action.SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        sendIntent.type = "text/plain"
        startActivity(main,Intent.createChooser(sendIntent,"dff"),null)
        return NanoHTTPD.newFixedLengthResponse("Intent send")

    }

    private fun handleQuery(session: IHTTPSession): Response? {
        val queryParameterString = session.queryParameterString
        if (queryParameterString != null) {
            Log.d("HWLLO", session.queryParameterString)
            if (queryParameterString.contains(".")) {
                return returnFile(queryParameterString);
            } else {
                return newFixedLengthResponse(setup("", queryParameterString));

            }
            return newFixedLengthResponse(setup("", session.queryParameterString))
        } else {
            return newFixedLengthResponse(setup("", filePath));

        }
    }




    private fun handleDevice(requestV1: String): Response? {
when{
    requestV1.substringAfter("/device").isEmpty()->{
        val device = DeviceResponse(
                Build.MANUFACTURER,
                Build.MODEL,DiskUtils.busySpace(true),DiskUtils.totalSpace(true),DiskUtils.freeSpace(true)
        )

        val jsonAdapter = Moshi.Builder()
                .build().adapter(DeviceResponse::class.java)




        val newFixedLengthResponse1 = newFixedLengthResponse(jsonAdapter.contactsToJson(device))
        newFixedLengthResponse1.addHeader("Access-Control-Allow-Origin","*")

        return newFixedLengthResponse1
    }
    requestV1.substringAfter("/device").contains("audio")->{
       return MediaRequestHandler.handleRequest(main,requestV1);
    }



}
        return newFixedLengthResponse("ApiCommand not found")



    }



    private fun handlePullCommand(session: IHTTPSession): Response? {
        when(session.method){
            Method.POST->{
                var map = HashMap<String, String>()
                session.parseBody(map)


                val jsonAdapter = Moshi.Builder()
                        .build().adapter(DownloadRequest::class.java)
                val get1 = jsonAdapter.fromJson(map[("postData")])


                val retrofit = Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl("https://f-droid.org/repo/")

                        .build()

                val fdr: Fdroid = retrofit.create(Fdroid::class.java)

                //"https://f-droid.org/repo/de.danoeh.antennapod_1060402.apk"
                fdr.downloadPodcast(get1?.url ?: "").subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread()).subscribe({ result ->

                    FileUtils(this).saveToDisk(result)

                    Log.d("Result", "There are ${result} Java developers in Lagos")


                }, { error ->
                    error.printStackTrace()
                })
                return NanoHTTPD.newFixedLengthResponse("OccKAY")


            }

            Method.GET->{
                val queryParameterString = session.queryParameterString

                when{
                    queryParameterString.startsWith("apk") -> {
                        val packageName= queryParameterString.substringAfter("apk/")

                        return handleApkDowload(packageName)

                    }
                    queryParameterString.startsWith("file") -> {
                        val req= queryParameterString.substringAfter("file/")

                        return returnFile(req);


                    }

                }

                return NanoHTTPD.newFixedLengthResponse("EMTPY")
            }
            else ->{
                return NanoHTTPD.newFixedLengthResponse("EMTPY")

            }
        }



    }

    private fun handleApkDowload(req: String): Response? {
        val pm = main.packageManager
//		File src = new File(info.applicationInfo.sourceDir);
        val packages = pm.getInstalledPackages(PackageManager.GET_META_DATA)

        for (package2 in packages) {
            print(package2.applicationInfo.sourceDir)
        }

        for (allInstalledApplication in AppUtils.getAllInstalledApplications(main)) {

            val packageName = allInstalledApplication.packageName//pm.getApplicationLabel(allInstalledApplication).toString()

            if (packageName.equals(req)) {
                Log.d("THIS", allInstalledApplication.sourceDir)


                // copy(src,dst)

                var fis: FileInputStream? = null
                fis = FileInputStream(allInstalledApplication.sourceDir)


                return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, getMimeType(allInstalledApplication.sourceDir), fis)
            }
        }
        return NanoHTTPD.newFixedLengthResponse("Package Not Found")
    }



    @Throws(IOException::class)
    private fun copy(src: File, dst: File) {
        val inStream = FileInputStream(src)
        val outStream = FileOutputStream(dst)
        val inChannel = inStream.channel
        val outChannel = outStream.channel
        inChannel.transferTo(0, inChannel.size(), outChannel)
        inStream.close()
        outStream.close()
    }


    private fun setup(prefix: String,filePath: String): String {
        val directory = File(filePath)
        val devices = directory.listFiles()
        Log.d("Files", "Size: " + devices?.size)
        var fileses = arrayListOf<FileResponse>()
        var s2 = ""
        if (devices != null) {
            for (file in devices) {
                fileses.add(FileResponse(file.name,file.path))
                Log.d("Files", "FileName:" + file.getName())

                s2 = (s2
                        + " <a href=\""
                        + prefix+"?"+filePath+"/"+file.getName()
                        + "\">"
                        + file.getName()
                        + "</a></br>\n")
            }
        }

        val moshi = Moshi.Builder()
                .build()


        val listMyData = Types.newParameterizedType(List::class.java, FileResponse::class.java)
        val adapter = moshi.adapter<List<FileResponse>>(listMyData)

        return s2

    }

    private fun returnFile(filePath: String): Response? {
        var fis: FileInputStream? = null
        fis = FileInputStream(filePath)


        return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, getMimeType(filePath), fis)


    }

    private fun returnAssetFile(filePath: String): Response? {

        val stream = main.getAssets().open(filePath)


        return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, getMimeType(filePath), stream)


    }


    private fun getMimeType(fileUrl: String): String {
        val extension = MimeTypeMap.getFileExtensionFromUrl(fileUrl)
        //NanoHTTPD.mimeTypes().get("json")
        return NanoHTTPD.mimeTypes().get(extension)?:"*"
    }

}
*/