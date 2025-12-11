using UnityEngine;

public class PipeControll : MonoBehaviour
{
    // Start is called once before the first execution of Update after the MonoBehaviour is created

    public float speed;

   // public int contador = 0;

    public float xLimit;

   // int ultimoIncremento = 0;
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {

        if (transform.position.x < -xLimit)
        {
            transform.position = new Vector3(xLimit, Random.Range(3, 17), 0.0f);
           // contador++;

          //  if (contador % 7 == 0 && contador != ultimoIncremento)
         //   {
          //      speed += 2;
          //      ultimoIncremento = contador;
         //   }
        }

        transform.position += new Vector3(-speed * Time.deltaTime, 0, 0);


        // rb_body.AddForce(Vector3.left * gravityForce, ForceMode.Force);
    }


}
