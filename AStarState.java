package com.lab3;

import java.util.HashMap;
import java.util.Iterator;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;
    /** два (нестатических) поля в класс AStarState с таким типом, одно
     для "открытых вершин" и другой для "закрытых вершин"**/
    public HashMap<Location, Waypoint> opened;
    public HashMap <Location, Waypoint> closed;
    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
        opened = new HashMap<>();
        closed = new HashMap<>();
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * Эта функция должна проверить все вершины в наборе открытых вершин,
     * и после этого она должна вернуть ссылку на вершину с наименьшей общей
     * стоимостью. Если в "открытом" наборе нет вершин, функция возвращает
     * NULL.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if (numOpenWaypoints() == 0)
            return null;
        Iterator iter = opened.values().iterator();
        Waypoint min = (Waypoint)iter.next();
        for (Waypoint w = min; iter.hasNext(); w = (Waypoint)iter.next())
            if (w.getTotalCost() < min.getTotalCost())
                min = w;
        return min;
    }

    /**
     *  Если в наборе «открытых вершин» в настоящее время нет вершины
     * для данного местоположения, то необходимо просто добавить новую вершину.
     *  Если в наборе «открытых вершин» уже есть вершина для этой
     * локации, добавьте новую вершину только в том случае, если стоимость пути до
     * новой вершины меньше стоимости пути до текущей.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        Location newLoc = newWP.getLocation();
        Waypoint w = opened.get(newLoc);
        if (w == null || newWP.getPreviousCost() < w.getPreviousCost())
        {
            opened.put(newLoc, newWP);
            return true;
        }
        return false;
    }
    /** Этот метод возвращает количество точек в наборе открытых вершин. **/
    public int numOpenWaypoints()
    {
        return opened.size();
    }
    /**
     * Эта функция перемещает вершину из набора «открытых вершин» в набор
     * «закрытых вершин». Так как вершины обозначены местоположением, метод
     * принимает местоположение вершины.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint v = opened.remove(loc);
        closed.put(loc,v);
    }

    /**
     * Эта функция должна возвращать значение true, если указанное
     * местоположение встречается в наборе закрытых вершин, и false в противном
     * случае.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closed.containsKey(loc);
    }
}