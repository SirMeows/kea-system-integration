import os
import re
from fastapi import FastAPI, Form, File, UploadFile
import aiofiles
from typing import Optional
from datetime import datetime
from uuid_extensions import uuid7str

# Looks for environment variable for upload directory
# If not found, creates default 
UPLOAD_DIR = os.path.abspath(os.getenv("UPLOAD_DIR", "./uploads"))

def handle_upload_directory():
    # Create folder if it doesn't exist
    # Only create .gitkeep if folder was freshly created
    if not os.path.exists(UPLOAD_DIR):
        os.makedirs(UPLOAD_DIR, exist_ok=True)
        with open(os.path.join(UPLOAD_DIR, ".gitkeep"), "w") as f:
            pass

handle_upload_directory()

def generate_safe_filename(original_filename: str) -> str:
    safe_name = re.sub(r'[^\w\-_\.]', '_', original_filename)
    unique_id = uuid7str()
    return f"{unique_id}__{safe_name}"

app = FastAPI()

@app.post("/form")
def basic_form(username: str = Form(...), password: str = Form(default=..., min_length=8)):
    print(username, password)
    return { "username": username }

@app.post("/fileform")
async def file_form(file: UploadFile = File(...), description: Optional[str] = None):
    unique_filename = generate_safe_filename(file.filename)
    file_path = os.path.join(UPLOAD_DIR, unique_filename)
    print(file_path)

    async with aiofiles.open(file_path, "wb") as f:
        while content := await file.read(1024): # read in chunks of 1024
            await f.write(content)