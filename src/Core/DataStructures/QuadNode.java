package Core.DataStructures;

import Core.Enums.CaseColor;

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
}
