using TMPro;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.SceneManagement;

public class BirdController : MonoBehaviour
{
    public float gravityForce;
    public float jumpForce;
    public Rigidbody rb_body;
    public int Score = 0;
    public GameObject pauseMenuGO;
    public GameObject Scorearriba;
    private TMP_Text textoScore;
    private TMP_Text textoScore2;
    void Start()
    {
        rb_body = GetComponent<Rigidbody>();
        textoScore = Scorearriba.GetComponent<TMP_Text>();
        textoScore2 = pauseMenuGO.transform.GetChild(3).GetComponent<TMP_Text>();
    }
    void Update()
    {
        rb_body.AddForce(Vector3.down * gravityForce * Time.deltaTime, ForceMode.Force);
        if (Input.GetKeyDown(KeyCode.Space) == true)
        {

            rb_body.linearVelocity = Vector3.zero;
            rb_body.AddForce(Vector3.up * jumpForce, ForceMode.Impulse);
        }
    }

    private void OnCollisionEnter(Collision collision)
    {
        //Debug.Log("He chocado con: " + collision.gameObject.tag);
        if (collision.gameObject.tag == "Pipe")
        {
            Time.timeScale = 0.0f;
            PauseMenu();
        }
    }

    public void PauseMenu()
    {
        pauseMenuGO.SetActive(true);
        Scorearriba.SetActive(false);
    }

    public void Restart()
    {
        SceneManager.LoadScene(1);
    }

    private void OnTriggerExit(Collider other)
    {
        Debug.Log("He chocado con un trigger" + other.gameObject.name);
        Score++;

        textoScore.text = Score.ToString();
        textoScore2.text = Score.ToString();

    }
}
