package Core.DataStructures;

import Cameleon.Enums.CaseColor;

public class QuadNode
{
    private QuadPoint pos;
    private CaseColor color; // la donnée stockée ici la couleur

    public QuadNode(QuadPoint _pos, CaseColor _color)
    {
        pos = _pos;
        color= _color;
    }

    public QuadPoint getPos() {
        return pos;
    }

    public CaseColor getColor() {
        return color;
    }

    public void setColor(CaseColor _color) {
        color = _color;
    }

    public String toString() {
        return "X = " + pos.getX() + " : Y = " + pos.getY() + " - Color = " + color;
    }

}
