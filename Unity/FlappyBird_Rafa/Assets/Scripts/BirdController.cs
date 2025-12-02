using UnityEngine;

public class BirdController : MonoBehaviour
{
    public float gravityForce;
    public float jumpForce; 
    public Rigidbody rb_body;
    void Start()
    {
        rb_body = GetComponent<Rigidbody>();
    }
    void Update()
    {
        rb_body.AddForce(Vector3.down * gravityForce * Time.deltaTime, ForceMode.Force);
        if (Input.GetKeyDown(KeyCode.Space)==true)
        {

            rb_body.linearVelocity = Vector3.zero;
            rb_body.AddForce(Vector3.up * jumpForce, ForceMode.Impulse);
        }
    }
}
