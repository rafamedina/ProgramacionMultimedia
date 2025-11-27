using UnityEngine;

public class PipeControll : MonoBehaviour
{
    // Start is called once before the first execution of Update after the MonoBehaviour is created
    public float gravityForce;
    public Rigidbody rb_body;
    void Start()
    {
        rb_body = GetComponent<Rigidbody>();
    }

    // Update is called once per frame
    void Update()
    {


        rb_body.AddForce(Vector3.left * gravityForce, ForceMode.Force);
    }
}
