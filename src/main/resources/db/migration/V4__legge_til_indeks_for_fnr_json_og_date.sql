CREATE INDEX IF NOT EXISTS fnr_index ON public.vurdering USING btree ((((json -> 'datagrunnlag'::text) ->> 'fnr'::text)))
CREATE INDEX IF NOT EXISTS jsonb_index ON public.vurdering USING gin (json)
CREATE INDEX date_index ON public.vurdering USING btree (date);