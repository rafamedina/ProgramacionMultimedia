using UnityEngine;
using UnityEngine.Rendering;

public class BirdController : MonoBehaviour
{
    public float gravityForce;
    public float jumpForce;
    public Rigidbody rb_body;
    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
        Debug.Log("Hola soy el start"); 
        rb_body = GetComponent<Rigidbody>();
    }

    // Update is called once per frame
    void Update()
    {
        //Dar una fuerza de gravedad
        rb_body.AddForce(Vector3.down*gravityForce, ForceMode.Force);

        if (Input.GetKeyDown(KeyCode.Space))
        {
            rb_body.AddForce(Vector3.down * jumpForce, ForceMode.Impulse);
            //Darle una fuerza up
            // Debug.Log("Hola soy el Update");

        }

    }
}
