using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.SocialPlatforms.Impl;

public class DataSaver : MonoBehaviour
{

    public BirdController bird;

    public void Start()
    {
        Load();

    }

    private void Update()
    {
        Save();
    }
    // Start is called before the first frame update
    public void Save()
    {
        BinaryFormatter bf = new BinaryFormatter();
        FileStream file = File.Create(Application.persistentDataPath + "/MyGameData.dat");

        MyData sharedData = new MyData();
        sharedData.stats.current_life = bird.Score;
        sharedData.stats.name = "luisja";

        bf.Serialize(file, sharedData);
        file.Close();
    }

     public void Load()
    {
        if (File.Exists(Application.persistentDataPath + "/MyGameData.dat"))
        {
            BinaryFormatter bf = new BinaryFormatter();
            FileStream fs = File.Open(Application.persistentDataPath + "/MyGameData.dat", FileMode.Open);
            MyData sharedData = bf.Deserialize(fs) as MyData;
            fs.Close();

            if (sharedData != null)
            {
                Debug.Log("Vida actual: " + sharedData.stats.current_life);
                Debug.Log("Nombre: " + sharedData.stats.name);
            }
        }
    }


}
