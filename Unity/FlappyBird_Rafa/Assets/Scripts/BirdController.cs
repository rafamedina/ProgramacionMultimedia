using UnityEngine;

public class BirdController : MonoBehaviour
{
    public float gravityForce;
    public float jumpForce; // Le pongo un valor por defecto
    public Rigidbody rb_body;

    void Start()
    {
        rb_body = GetComponent<Rigidbody>();
    }

    void Update()
    {
        rb_body.AddForce(Vector3.down * gravityForce, ForceMode.Force);
        // 1. EL SALTO (INPUT)
        // El input siempre debe ir en Update para que no pierda pulsaciones
        if (Input.GetKeyDown(KeyCode.Space))
        {
            // CORRECCI�N IMPORTANTE:
            // Antes ten�as Vector3.down (Abajo). Lo cambiamos a Vector3.up (Arriba).

            // Truco Pro: Para juegos tipo Flappy Bird, es mejor resetear la velocidad 
            // a cero antes de saltar. Si no, si el p�jaro cae muy r�pido, 
            // el salto apenas frenar� la ca�da.
            rb_body.linearVelocity = Vector3.zero;

            rb_body.AddForce(Vector3.up * jumpForce, ForceMode.Impulse);
        }
    }

    // 2. LA F�SICA CONSTANTE
    // Todo lo que sea "fuerzas constantes" (como tu gravedad manual) deber�a ir
    // preferiblemente en FixedUpdate, no en Update, para que sea suave.
    /* void FixedUpdate() 
    {
        // Solo descomenta esto si realmente necesitas gravedad extra.
        // Normalmente basta con activar "Use Gravity" en el inspector del Rigidbody.
        // rb_body.AddForce(Vector3.down * gravityForce, ForceMode.Force);
    }
    */
}
