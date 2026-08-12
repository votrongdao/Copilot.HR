# class BaseRepository:
#     def __init__(self, model):
#         self.model = model
#
#     def get_all(self, **filters):
#         return self.model.objects.filter(**filters)
#
#     def get_by_id(self, id):
#         try:
#             return self.model.objects.get(id=id)
#         except self.model.DoesNotExist:
#             return None
#
#     def create(self, **data):
#         return self.model.objects.create(**data)
#
#     def update(self, instance, **data):
#         for key, value in data.items():
#             setattr(instance, key, value)
#         instance.save()
#         return instance
#
#     def delete(self, instance):
#         instance.delete()

import json
import os
import uuid
from django.conf import settings

DATA_FILE = os.path.join(settings.BASE_DIR, 'data.json')

def load_data():
    if not os.path.exists(DATA_FILE):
        return {}
    with open(DATA_FILE, 'r', encoding='utf-8') as f:
        try:
            return json.load(f)
        except json.JSONDecodeError:
            return {}

def save_data(data):
    with open(DATA_FILE, 'w', encoding='utf-8') as f:
        json.dump(data, f, indent=4, ensure_ascii=False)

class BaseRepository:
    def __init__(self, model):
        self.table_name = model.__name__

    def get_all(self, **filters):
        data = load_data()
        records = data.get(self.table_name, [])
        
        for key, value in filters.items():
            records = [r for r in records if r.get(key) == value]
            
        return records

    def get_by_id(self, id):
        data = load_data()
        records = data.get(self.table_name, [])
        for r in records:
            if str(r.get('id')) == str(id):
                return r
        return None

    def create(self, **data_kwargs):
        full_data = load_data()
        if self.table_name not in full_data:
            full_data[self.table_name] = []
        
        if 'id' not in data_kwargs:
            data_kwargs['id'] = str(uuid.uuid4())
            
        full_data[self.table_name].append(data_kwargs)
        save_data(full_data)
        return data_kwargs

    def update(self, instance, **data_kwargs):
        full_data = load_data()
        records = full_data.get(self.table_name, [])
        
        target_id = str(instance.get('id')) if isinstance(instance, dict) else str(instance)
            
        updated_record = None
        for i, r in enumerate(records):
            if str(r.get('id')) == target_id:
                records[i].update(data_kwargs)
                updated_record = records[i]
                break
                
        if updated_record:
            full_data[self.table_name] = records
            save_data(full_data)
        return updated_record

    def delete(self, instance):
        full_data = load_data()
        records = full_data.get(self.table_name, [])
        
        target_id = str(instance.get('id')) if isinstance(instance, dict) else str(instance)
            
        initial_length = len(records)
        records = [r for r in records if str(r.get('id')) != target_id]
        
        if len(records) < initial_length:
            full_data[self.table_name] = records
            save_data(full_data)
            return True
        return False
