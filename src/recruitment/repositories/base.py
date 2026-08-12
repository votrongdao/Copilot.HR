from django.forms.models import model_to_dict

class BaseRepository:
    def __init__(self, model):
        self.model = model

    def _to_dict(self, obj):
        if not obj: return None
        d = model_to_dict(obj)
        d['id'] = str(obj.id)
        for k, v in d.items():
            if hasattr(v, '__float__') and not isinstance(v, float):
               d[k] = float(v) 

            import uuid
            if isinstance(v, uuid.UUID):
               d[k] = str(v)
        return d

    def get_all(self, **filters):
        queryset = self.model.objects.filter(**filters)
        return [self._to_dict(obj) for obj in queryset]

    def get_by_id(self, id):
        try:
            obj = self.model.objects.get(id=id)
            return self._to_dict(obj)
        except self.model.DoesNotExist:
            return None

    def create(self, **data):
        obj = self.model.objects.create(**data)
        return self._to_dict(obj)

    def update(self, instance_or_id, **data):
        obj_id = instance_or_id.get('id') if isinstance(instance_or_id, dict) else instance_or_id
        self.model.objects.filter(id=obj_id).update(**data)
        
        updated_obj = self.model.objects.get(id=obj_id)
        return self._to_dict(updated_obj)

    def delete(self, instance_or_id):
        obj_id = instance_or_id.get('id') if isinstance(instance_or_id, dict) else instance_or_id
        deleted, _ = self.model.objects.filter(id=obj_id).delete()
        return deleted > 0
