using System;
using UnityEngine;

[Serializable]
public class MyData 
{
    [Serializable]
    public struct PlayerStats
    {
        public int current_life;
        public string name;

    }

    [SerializeField]
    public PlayerStats stats;
}
