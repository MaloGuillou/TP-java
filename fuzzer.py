import subprocess
import sys
import os
import time
from google import genai

# Use the model from your documentation
client = genai.Client(api_key="AIzaSyDJzem6bhgyeYHcw58StyLdvRIT6FIVCTw")
MODEL_NAME = "gemini-2.5-flash-lite"
model_info = client.models.get(model=MODEL_NAME)
print(f"{model_info.input_token_limit=}")
print(f"{model_info.output_token_limit=}")

RULES_CONTEXT = """
You are a QA Tester. Verify that the game is functional
"""

process = subprocess.Popen(
    ['stdbuf', '-oL', '-eL', './run.sh'],
    stdin=subprocess.PIPE,
    stdout=subprocess.PIPE,
    stderr=subprocess.STDOUT,
    text=True,
    bufsize=1
)

def run_test():
    print("--- AI WRAPPER STARTED ---")
    os.set_blocking(process.stdout.fileno(), False)

    while True:
        output_buffer = ""
        last_data_time = time.time()

        # Read everything available until the game pauses for > 0.7 seconds
        while True:
            try:
                char = process.stdout.read(1)
                if char:
                    output_buffer += char
                    sys.stdout.write(char)
                    sys.stdout.flush()
                    last_data_time = time.time()
                else:
                    if output_buffer and (time.time() - last_data_time > 7.0):
                        break
                    time.sleep(0.05)
                    if process.poll() is not None: return
            except Exception:
                break

        if not output_buffer:
            continue

        try:
            prompt = (
                f"{RULES_CONTEXT}\n\n"
                f"Current Game Screen:\n{output_buffer}\n\n"
                "INSTRUCTION: You must respond with ONLY the raw value. "
                "No bold, no quotes, no markdown, no explanation. "
                "If it's a name, just the name. If [Y/n], type 'y' or 'n'. If a menu, type the number of the option (e.g. -1,0,1,2,3,4,...)."
            )
            response = client.models.generate_content(model=MODEL_NAME, contents=prompt)
            decision = response.text.strip().replace("*", "").replace("`", "")
            if any(char.isdigit() for char in decision) and len(decision) < 3:
                decision = "".join(filter(str.isdigit, decision))

        except Exception as e:
            print(f"\n[API Error: {e}]")
            time.sleep(10)
            decision = "y" if "[Y/n]" in output_buffer else "1"

        print(f"\n[AI Decision: {decision}]")
        process.stdin.write(decision + "\n")
        process.stdin.flush()

if __name__ == "__main__":
    run_test()