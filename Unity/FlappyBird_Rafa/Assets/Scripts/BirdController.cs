using System;
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

    private bool pausePanelInterruptor = true;
    private bool isAlive = true;
    public AudioSource sFXAudioSource;
    public AudioSource sFXAudioTap;
    public AudioSource sFXAudioMusic;

    void Start()
    {
       // sFXAudioSource = GetComponent<AudioSource>();
     //   sFXAudioSource2 = GetComponent<AudioSource>();
        rb_body = GetComponent<Rigidbody>();
        textoScore = Scorearriba.GetComponent<TMP_Text>();
        textoScore2 = pauseMenuGO.transform.GetChild(3).GetComponent<TMP_Text>();
        sFXAudioMusic.Play();

    }
    void Update()
    {
        rb_body.AddForce(Vector3.down * gravityForce * Time.deltaTime, ForceMode.Force);
        if (Input.GetKeyDown(KeyCode.Space) == true)
        {
            sFXAudioTap.Play();
            rb_body.linearVelocity = Vector3.zero;
            rb_body.AddForce(Vector3.up * jumpForce, ForceMode.Impulse);

        }
        if (Input.GetKeyDown(KeyCode.Escape) == true && pausePanelInterruptor ==true)
        {
            PauseMenu(true);
            pausePanelInterruptor = false;

        }

        else if (Input.GetKeyDown(KeyCode.Escape) == true && pausePanelInterruptor == false)
        {
            PauseMenu(false);
            pausePanelInterruptor = true;

        }

    }

    private void OnCollisionEnter(Collision collision)
    {
        //Debug.Log("He chocado con: " + collision.gameObject.tag);
        if (collision.gameObject.tag == "Pipe")
        {
            PauseDeath();

        }
    }

    public void PauseMenu(bool activado)
    {
        if (activado)
        {
            Time.timeScale = 0.0f;
            pauseMenuGO.SetActive(true);
            Scorearriba.SetActive(false);
        }
        else if (!activado && isAlive)
        {
            Time.timeScale = 1.0f;
            pauseMenuGO.SetActive(false);
            Scorearriba.SetActive(true);
        }

    }

    public void PauseDeath()
    {
        isAlive = false;
        sFXAudioMusic.Pause();
   
            Time.timeScale = 0.0f;
            pauseMenuGO.SetActive(true);
            Scorearriba.SetActive(false);

            sFXAudioSource.Play();
        
       

    }
    public void Restart()
    {

        SceneManager.LoadScene(1);
        Time.timeScale = 1.0f;

    }

    private void OnTriggerExit(Collider other)
    {
        Debug.Log("He chocado con un trigger" + other.gameObject.name);
        Score++;

        textoScore.text = Score.ToString();
        textoScore2.text = Score.ToString();

    }
}
