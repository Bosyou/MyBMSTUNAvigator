package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.mapview.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.io.IOException

class MainActivity : AppCompatActivity() {
    companion object {
        public val FIRST_LEVEL = 1;
        public val SECOND_LEVEL = 2;
    }

    lateinit var podsAdapter: ArrayAdapter<String>
    lateinit var list: MutableList<String>
    lateinit var button: MaterialButton
    private var mapView: MapView? = null

    private var markLayer: MarkLayer? = null
    private var routeLayer: RouteLayer? = null

    private var nodes: List<Pair<Int, PointF>>? = null
    private var nodesContract: List<PointF>? = null
    private var marksTest: Map<String, PointF>? = null
    lateinit var nameStart: String
    lateinit var nameEnd: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMapView()
        initViews()
        openStartMap(FIRST_LEVEL);
    }

    fun initMapView() {
        mapView = findViewById<MapView>(R.id.mapview)
        routeLayer = RouteLayer(mapView)
        mapView!!.addLayer(routeLayer)
        markLayer = MarkLayer(mapView)
        mapView!!.addLayer(markLayer)
    }

    fun openStartMap(floor: Int) {
        nodes = TestData.getNodesList()
        nodesContract = TestData.getNodesContactList()
        marksTest = TestData.getMarks()
        nameStart = "1 этаж Главная Лестница";
        nameEnd = "1 этаж Главная Лестница";
        var startPoint: PointF? = marksTest!!.get(nameStart);
        var endPoint: PointF? = marksTest!!.get(nameEnd);
        lateinit var oldStartPoint: PointF;
        lateinit var oldNameStart: String;
        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeStream(assets.open("${floor}level.png"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        MapUtils.init(nodes!!.size, nodesContract!!.size)
        mapView!!.loadMap(bitmap)
        mapView!!.setMapViewListener(object : MapViewListener {
            override fun onMapLoadSuccess() {
                markLayer!!.marks = marksTest;
                markLayer!!.setFloor(floor);
                routeLayer!!.setFloor(floor);
                markLayer!!.setOneTouchMarkIsClickListener { num ->
                    oldNameStart = nameStart
                    nameStart = num;
                    oldStartPoint = startPoint!!
                    startPoint =
                        PointF(marksTest!!.get(nameStart)!!.x, marksTest!!.get(nameStart)!!.y)
                    val routeList =
                        MapUtils.getShortestDistanceBetweenTwoPoints(
                            startPoint,
                            nameStart,
                            endPoint,
                            nameEnd,
                            nodes,
                            nodesContract
                        )
                    routeLayer!!.setNodeList(nodes)
                    routeLayer!!.setRouteList(routeList)
                    mapView!!.refresh()
                }
                markLayer!!.setTwoTouchMarkIsClickListener { num ->
                    nameEnd = num
                    endPoint = PointF(marksTest!!.get(nameEnd)!!.x, marksTest!!.get(nameEnd)!!.y)
                    startPoint = oldStartPoint
                    nameStart = oldNameStart
                    val routeList =
                        MapUtils.getShortestDistanceBetweenTwoPoints(
                            startPoint,
                            nameStart,
                            endPoint,
                            nameEnd,
                            nodes,
                            nodesContract
                        )
                    routeLayer!!.setNodeList(nodes)
                    routeLayer!!.setRouteList(routeList)
                    mapView!!.refresh()
                }
                mapView!!.refresh()
            }

            override fun onMapLoadFail() {}
        })

    }

    fun openMapOfFloor(floor: Int) {
        lateinit var oldStartPoint: PointF;
        lateinit var oldNameStart: String;
        var startPoint: PointF? = marksTest!!.get(nameStart);
        var endPoint: PointF? = marksTest!!.get(nameEnd);
        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeStream(assets.open("${floor}level.png"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mapView!!.loadMap(bitmap)
        mapView!!.setMapViewListener(object : MapViewListener {
            override fun onMapLoadSuccess() {
                markLayer!!.marks = marksTest;
                markLayer!!.setFloor(floor);
                routeLayer!!.setFloor(floor);
                markLayer!!.setOneTouchMarkIsClickListener { num ->
                    oldNameStart = nameStart
                    nameStart = num;
                    oldStartPoint = startPoint!!
                    startPoint =
                        PointF(marksTest!!.get(nameStart)!!.x, marksTest!!.get(nameStart)!!.y)
                    val routeList =
                        MapUtils.getShortestDistanceBetweenTwoPoints(
                            startPoint,
                            nameStart,
                            endPoint,
                            nameEnd,
                            nodes,
                            nodesContract
                        )
                    routeLayer!!.setNodeList(nodes)
                    routeLayer!!.setRouteList(routeList)
                    mapView!!.refresh()
                }
                markLayer!!.setTwoTouchMarkIsClickListener { num ->
                    nameEnd = num
                    endPoint = PointF(marksTest!!.get(nameEnd)!!.x, marksTest!!.get(nameEnd)!!.y)
                    startPoint = oldStartPoint
                    nameStart = oldNameStart
                    val routeList =
                        MapUtils.getShortestDistanceBetweenTwoPoints(
                            startPoint,
                            nameStart,
                            endPoint,
                            nameEnd,
                            nodes,
                            nodesContract
                        )
                    routeLayer!!.setNodeList(nodes)
                    routeLayer!!.setRouteList(routeList)
                    mapView!!.refresh()
                }
                mapView!!.refresh()
            }

            override fun onMapLoadFail() {}
        })
    }

    fun initViews() {
        val autoCompleteTextViewFrom: AutoCompleteTextView= findViewById(R.id.text_input)
        val autoCompleteTextViewTo: AutoCompleteTextView= findViewById(R.id.text_inputto)
        val listMarkName = TestData.getMarksName()
        val adapter = ArrayAdapter(this, R.layout.list_item, listMarkName)
        autoCompleteTextViewFrom.setAdapter(adapter)
        autoCompleteTextViewTo.setAdapter(adapter)
        list = mutableListOf<String>()
        button = findViewById(R.id.button)
        val buttonFirstLevel = findViewById<MaterialButton>(R.id.first_level_button)
        val buttonSecondLevel = findViewById<MaterialButton>(R.id.second_level_button)
        buttonFirstLevel.setOnClickListener {
            openMapOfFloor(FIRST_LEVEL)
        }
        buttonSecondLevel.setOnClickListener {
            openMapOfFloor(SECOND_LEVEL)
        }
        button.setOnClickListener {
            val routeList =
                MapUtils.getShortestDistanceBetweenTwoPoints(
                    marksTest!!.get(autoCompleteTextViewFrom.text.toString()),
                    autoCompleteTextViewFrom.text.toString(),
                    marksTest!!.get(autoCompleteTextViewTo.text.toString()),
                    autoCompleteTextViewTo.text.toString(),
                    nodes,
                    nodesContract
                )
            routeLayer!!.setNodeList(nodes)
            routeLayer!!.setRouteList(routeList)
            mapView!!.refresh()
        }
        podsAdapter = ArrayAdapter<String>(
            application,
            R.layout.itemlist,
            R.id.title,
            list
        )
    }

}